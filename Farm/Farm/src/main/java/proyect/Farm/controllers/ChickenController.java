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
import proyect.Farm.entities.Chicken;
import proyect.Farm.services.ChickenService;

import java.util.List;

@RestController
@RequestMapping("api/chickens")
@Tag(name = "Chicken Controller", description = "Controls to manage chickens in the farm")
public class ChickenController {
    @Autowired
    private ChickenService chickenService;

    @Operation(summary = "Get all chickens", description = "Retrieve a list of all chickens in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chickens retrieved successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Chicken.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @GetMapping
    public ResponseEntity<List<Chicken>> getAllChickens(){
        try {
            List<Chicken> chickens = chickenService.findAll();
            return ResponseEntity.ok(chickens);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Get all alive chickens by farm ID", description = "Retrieve a list of all alive chickens for a specific farm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alive chickens retrieved successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Chicken.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @GetMapping("/{farmId}")
    public ResponseEntity<List<Chicken>> getAllAliveChickens(@PathVariable Long farmId){
        try {
            List<Chicken> chickens = chickenService.findChickensByStatus(farmId, true);
            return ResponseEntity.ok(chickens);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Create a new chicken", description = "Create a new chicken for a specific farm",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body to create new farm",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"ageInDays\": 21, \"kindOfChicken\": \"bought\", \"price\": 45.0}")
                    )))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chicken created successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Chicken.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @PostMapping("/{farmId}")
    public ResponseEntity<Chicken> createChicken(@PathVariable Long farmId, @RequestBody Chicken chicken){
        try {
            Chicken savedChicken = chickenService.save(chicken, farmId);
            return ResponseEntity.ok(savedChicken);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
