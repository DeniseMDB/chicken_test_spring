package proyect.Farm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyect.Farm.entities.Chicken;
import proyect.Farm.services.ChickenService;

import java.util.List;

@RestController
@RequestMapping("api/chickens")
public class ChickenController {
    @Autowired
    private ChickenService chickenService;

    @GetMapping
    public ResponseEntity<List<Chicken>> getAllChickens(){
        List<Chicken> chickens = chickenService.findAll();
        return ResponseEntity.ok(chickens);
    }

    @GetMapping("/alive")
    public ResponseEntity<List<Chicken>> getAllAliveChickens(){
        List<Chicken> chickens = chickenService.findAllAlive();
        return ResponseEntity.ok(chickens);
    }

    @PostMapping("/{farmId}")
    public ResponseEntity<Chicken> createChicken(@PathVariable Long farmId, @RequestBody Chicken chicken){
        Chicken savedChicken = chickenService.save(chicken,farmId);
        return ResponseEntity.ok(savedChicken);
    }
}
