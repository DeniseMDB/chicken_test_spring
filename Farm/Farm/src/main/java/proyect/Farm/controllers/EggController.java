package proyect.Farm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyect.Farm.entities.Egg;
import proyect.Farm.services.EggService;

import java.util.List;

@RestController
@RequestMapping("/api/eggs")
public class EggController {
    @Autowired
    private EggService eggService;

    @GetMapping
    public ResponseEntity<List<Egg>> getAllEggs(){
        List<Egg> eggs = eggService.findAll();
        return ResponseEntity.ok(eggs);
    }

    @PostMapping
    public ResponseEntity<Egg> createEgg(@RequestBody Egg egg){
        Egg savedEgg = eggService.save(egg);
        return ResponseEntity.ok(savedEgg);
    }
}
