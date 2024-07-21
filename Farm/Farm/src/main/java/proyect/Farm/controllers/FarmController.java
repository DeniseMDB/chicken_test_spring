package proyect.Farm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyect.Farm.entities.Farm;
import proyect.Farm.services.ChickenService;
import proyect.Farm.services.FarmService;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/farm")
public class FarmController {
    @Autowired
    private FarmService farmService;
    @Autowired
    private ChickenService chickenService;

    @PostMapping
    public ResponseEntity<Farm> createFarm(@RequestBody Farm farm) {
        Farm savedFarm = farmService.saveFarm(farm);
        return ResponseEntity.ok(savedFarm);
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<Void> startSimulation(@PathVariable Long id) {
        farmService.startSimulation(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/{id}/stop")
    public ResponseEntity<Void> stopSimulation(@PathVariable Long id) {
        farmService.stopSimulation(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/{id}/pause")
    public ResponseEntity<Void> pauseSimulation(@PathVariable Long id) {
        farmService.pauseSimulation(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/{id}/resume")
    public ResponseEntity<Void> resumeSimulation(@PathVariable Long id) {
        farmService.resumeSimulation(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/{farmId}")
    public ResponseEntity<Farm> findById(@PathVariable Long farmId) {
        try {
            Optional<Farm> farmOpt = Optional.ofNullable(farmService.findById(farmId));
            if (farmOpt.isPresent()) {
                return ResponseEntity.ok(farmOpt.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.out.println("Error fetching farm with ID " + farmId + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{id}/buy-eggs")
    public ResponseEntity<Void>buyEggs(@PathVariable Long id,@RequestBody Map<String,Object> body){
        Integer amount = Integer.parseInt(body.get("amount").toString());
        Double pricePerEgg = Double.parseDouble(body.get("pricePerEgg").toString());
        try{
            farmService.buyEggs(amount, pricePerEgg,id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/{id}/buy-chickens")
    public ResponseEntity<Void>buyChickens(@PathVariable Long id,@RequestBody Map<String,Object> body){
        Integer amount = Integer.parseInt(body.get("amount").toString());
        Double pricePerChicken = Double.parseDouble(body.get("pricePerChicken").toString());
        Integer chickenAgeInDays = Integer.parseInt(body.get("chickenAgeInDays").toString());
        try{
            farmService.buyChickens(amount, pricePerChicken, chickenAgeInDays, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            System.out.println("Error buying chickens: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/sell-eggs")
    public ResponseEntity<Void>sellEggs(@PathVariable Long id, @RequestBody Map<String,Object> body){
        Integer amount = Integer.parseInt(body.get("amount").toString());
        try{
            farmService.sellEggs(amount, id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/sell-chickens")
    public ResponseEntity<Void>sellChickens(@PathVariable Long id, @RequestBody Map<String,Object> body){
        Integer amount = Integer.parseInt(body.get("amount").toString());
        try{
            farmService.sellChickens(amount,id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (RuntimeException e){
            System.out.println("Error selling chickens: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}



