package proyect.Farm.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyect.Farm.entities.Egg;
import proyect.Farm.services.EggService;

import java.util.List;

@RestController
@RequestMapping("api/eggs")
@Tag(name = "Egg Controller", description = "Controls to manage eggs in the farm")
public class EggController {
    @Autowired
    private EggService eggService;

    @Operation(summary = "Get all eggs", description = "Retrieve a list of all eggs in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eggs retrieved successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Egg.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @GetMapping
    public ResponseEntity<List<Egg>> getAllEggs(){
        try {
            List<Egg> eggs = eggService.findAll();
            return ResponseEntity.ok(eggs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Create a new egg", description = "Create a new egg for a specific farm",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body to create a new egg",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"price\": 0.50, \"ageInDays\": 0}")
                    )))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Egg created successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Egg.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @PostMapping("/{farmId}")
    public ResponseEntity<Egg> createEgg(@PathVariable Long farmId, @RequestBody Egg egg){
        try {
            Egg savedEgg = eggService.save(egg, farmId);
            return ResponseEntity.ok(savedEgg);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
