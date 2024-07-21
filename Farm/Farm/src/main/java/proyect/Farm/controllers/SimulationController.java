package proyect.Farm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyect.Farm.services.SimulationService;

@RestController
@RequestMapping("/api/simulation")
public class SimulationController {

    @Autowired
    private SimulationService simulationService;

    @PostMapping("/start/{farmId}")
    public ResponseEntity<String> startSimulation(@RequestParam("speed") Integer speed,@PathVariable Long farmId) {
        simulationService.startSimulation(farmId, speed);
        return ResponseEntity.ok("Simulation started for farm " + farmId+ " every "+speed+" second one day will go by.");
    }

    @PostMapping("/stop/{farmId}")
    public ResponseEntity<String> stopSimulation(@PathVariable Long farmId) {
        simulationService.stopSimulation(farmId);
        return ResponseEntity.ok("Simulation stopped for farm " + farmId);
    }

    @PostMapping("/pause/{farmId}")
    public ResponseEntity<String> pauseSimulation(@PathVariable Long farmId) {
        simulationService.pauseSimulation(farmId);
        return ResponseEntity.ok("Simulation paused for farm " + farmId);
    }

    @PostMapping("/resume/{farmId}")
    public ResponseEntity<String> resumeSimulation(@PathVariable Long farmId) {
        simulationService.resumeSimulation(farmId);
        return ResponseEntity.ok("Simulation resumed for farm " + farmId);
    }
}
