package proyect.Farm.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyect.Farm.entities.Farm;
import proyect.Farm.services.ChickenService;
import proyect.Farm.services.FarmService;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/farm")
@Tag(name = "Farm Controller", description = "Controls to manage a farm")
@SecurityRequirement(name = "basicAuth")
public class FarmController {
    @Autowired
    private FarmService farmService;
    @Autowired
    private ChickenService chickenService;

    @Operation(summary = "Create a new farm", description = "Creates a new farm in the system.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body to create new farm",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"money\": 1000.0, \"maxEggs\": 250, \"maxChickens\": 100, \"daysInBusiness\": 0}")
                    )))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Farm created successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Farm.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @PostMapping
    public ResponseEntity<Farm> createFarm(@RequestBody Farm farm) {
        Farm savedFarm = farmService.saveFarm(farm);
        return ResponseEntity.ok(savedFarm);
    }

    @Operation(summary = "Find farm by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Farm found successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Farm.class)) }),
            @ApiResponse(responseCode = "404", description = "Farm not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @GetMapping("/{farmId}")
    public ResponseEntity<Farm> findById(@PathVariable Long farmId) {
        Optional<Farm> farmOpt = Optional.ofNullable(farmService.findById(farmId));
        if (farmOpt.isPresent()) {
            return ResponseEntity.ok(farmOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Buy eggs for the farm", description = "Buy eggs to add to farm stock",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Please insert amount to buy and the price of each egg",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"amount\": 1, \"pricePerEgg\": 0.50}")
                    )))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eggs bought successfully",
                    content = { @Content(mediaType = "text/plain",
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "Eggs bought successfully. 1 eggs have been added to the farm stock."))}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @PostMapping("/{id}/buy-eggs")
    public ResponseEntity<String> buyEggs(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Integer amount = Integer.parseInt(body.get("amount").toString());
        Double pricePerEgg = Double.parseDouble(body.get("pricePerEgg").toString());
        farmService.buyEggs(amount, pricePerEgg, id);
        return ResponseEntity.ok(amount + " Eggs have been added to the farm stock");
    }

    @Operation(summary = "Buy chickens for the farm", description = "Choose amount to sell to earn money",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body for amount to sell input",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"amount\": 1, \"pricePerChicken\": 20.50, \"chickenAgeInDays\": 25}")
                    )))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chickens bought successfully",
                    content = { @Content(mediaType = "text/plain",
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "Chickens bought successfully. 1 chickens have been added to stock."))}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @PostMapping("/{id}/buy-chickens")
    public ResponseEntity<String> buyChickens(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Integer amount = Integer.parseInt(body.get("amount").toString());
        Double pricePerChicken = Double.parseDouble(body.get("pricePerChicken").toString());
        Integer chickenAgeInDays = Integer.parseInt(body.get("chickenAgeInDays").toString());
        farmService.buyChickens(amount, pricePerChicken, chickenAgeInDays, id);
        return ResponseEntity.ok("Chickens bought successfully. " + amount + " chickens have been added to stock.");
    }

    @Operation(summary = "Sell eggs from the farm", description = "Choose amount to sell to earn money",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body for amount to sell input",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"amount\": 1}")
                    )))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eggs sold successfully",
                    content = { @Content(mediaType = "text/plain",
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "Eggs sold successfully"))}),
            @ApiResponse(responseCode = "404", description = "Farm or eggs not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @PostMapping("/{id}/sell-eggs")
    public ResponseEntity<String> sellEggs(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Integer amount = Integer.parseInt(body.get("amount").toString());
        farmService.sellEggs(amount, id, null);
        return ResponseEntity.ok("Eggs sold successfully");
    }

    @Operation(summary = "Sell chickens from the farm", description = "Choose amount to sell to earn money",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body for amount to sell input",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"amount\": 1}")
                    )))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chickens sold successfully",
                    content = { @Content(mediaType = "text/plain",
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "Chickens sold successfully")) }),
            @ApiResponse(responseCode = "404", description = "Farm or chickens not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @PostMapping("/{id}/sell-chickens")
    public ResponseEntity<String> sellChickens(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Integer amount = Integer.parseInt(body.get("amount").toString());
        farmService.sellChickens(amount, id, null);
        return ResponseEntity.ok("Chickens sold successfully");
    }
}
