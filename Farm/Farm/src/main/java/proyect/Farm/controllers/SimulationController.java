package proyect.Farm.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyect.Farm.business.ISimulation;
import proyect.Farm.services.SimulationService;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/simulation")
@Tag(name = "Simulation Controller", description = "Simulates the passing of time (by days).")
public class SimulationController {

    @Autowired
    ISimulation iSimulation;

    public SimulationController(SimulationService simulationService) {
        this.iSimulation = simulationService;
    }

    @Operation(summary = "Start the simulation for a farm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Simulation started successfully",
                    content = { @Content(mediaType = "text/plain",
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "Simulation started successfully"))}),
            @ApiResponse(responseCode = "404", description = "Farm not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @PostMapping("/start/{farmId}")
    public ResponseEntity<String> startSimulation(@RequestParam("speed") Integer speed, @PathVariable Long farmId) {
        try {
            iSimulation.startSimulation(farmId, speed);
            return ResponseEntity.ok("Simulation started for farm " + farmId + " every " + speed + " second one day will go by.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body("Farm not found: " + farmId);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error starting simulation: " + e.getMessage());
        }
    }

    @Operation(summary = "Stop the simulation for a farm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Simulation stopped successfully",
                    content = { @Content(mediaType = "text/plain",
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "Simulation stopped successfully")) }),
            @ApiResponse(responseCode = "404", description = "Farm not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @PostMapping("/stop/{farmId}")
    public ResponseEntity<String> stopSimulation(@PathVariable Long farmId) {
        try {
            iSimulation.stopSimulation(farmId);
            return ResponseEntity.ok("Simulation stopped for farm " + farmId);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body("Farm not found: " + farmId);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error stopping simulation: " + e.getMessage());
        }
    }

    @Operation(summary = "Pause the simulation for a farm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Simulation paused successfully",
                    content = { @Content(mediaType = "text/plain",
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "Simulation paused successfully")) }),
            @ApiResponse(responseCode = "404", description = "Farm not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @PostMapping("/pause/{farmId}")
    public ResponseEntity<String> pauseSimulation(@PathVariable Long farmId) {
        try {
            iSimulation.pauseSimulation(farmId);
            return ResponseEntity.ok("Simulation paused for farm " + farmId);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body("Farm not found: " + farmId);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error pausing simulation: " + e.getMessage());
        }
    }

    @Operation(summary = "Resume the simulation for a farm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Simulation resumed successfully",
                    content = { @Content(mediaType = "text/plain",
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "Simulation resumed successfully")) }),
            @ApiResponse(responseCode = "404", description = "Farm not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @PostMapping("/resume/{farmId}")
    public ResponseEntity<String> resumeSimulation(@PathVariable Long farmId) {
        try {
            iSimulation.resumeSimulation(farmId);
            return ResponseEntity.ok("Simulation resumed for farm " + farmId);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body("Farm not found: " + farmId);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error resuming simulation: " + e.getMessage());
        }
    }
}
