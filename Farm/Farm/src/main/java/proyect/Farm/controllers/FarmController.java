package proyect.Farm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import proyect.Farm.services.FarmService;

import java.util.Map;

@RestController
@RequestMapping("api/farm")
public class FarmController {
    @Autowired
    private FarmService farmService;

    @PostMapping("/start")
    public ResponseEntity<Void> startSimulation() {
        farmService.startSimulation();
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/stop")
    public ResponseEntity<Void> stopSimulation() {
        farmService.stopSimulation();
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/pause")
    public ResponseEntity<Void> pauseSimulation() {
        farmService.pauseSimulation();
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/resume")
    public ResponseEntity<Void> resumeSimulation() {
        farmService.resumeSimulation();
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/buy-eggs")
    public ResponseEntity<Void>buyEggs(@RequestBody Map<String,Object> body){
        Integer amount = Integer.parseInt(body.get("amount").toString());
        Double pricePerEgg = Double.parseDouble(body.get("pricePerEgg").toString());
        try{
            farmService.buyEggs(amount, pricePerEgg);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/buy-chickens")
    public ResponseEntity<Void>buyChickens(@RequestBody Map<String,Object> body){
        Integer amount = Integer.parseInt(body.get("amount").toString());
        Double pricePerChicken = Double.parseDouble(body.get("pricePerChicken").toString());
        Integer chickenAgeInDays = Integer.parseInt(body.get("chickenAgeInDays").toString());
        try{
            farmService.buyChickens(amount, pricePerChicken, chickenAgeInDays);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



}



