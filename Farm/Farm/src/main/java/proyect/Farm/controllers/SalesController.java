package proyect.Farm.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import proyect.Farm.entities.Sales;
import proyect.Farm.services.SalesService;

import java.util.List;

@RestController
@RequestMapping("api/sales")
@Tag(name = "Sales Controller", description = "Controls to manage sales in the farm")
public class SalesController {
    @Autowired
    SalesService salesService;

    @Operation(summary = "Get sales by farm ID", description = "Retrieve a list of sales for a specific farm by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sales retrieved successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Sales.class)) }),
            @ApiResponse(responseCode = "404", description = "Sales not found for the specified farm ID",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @GetMapping("/{farmId}")
    public ResponseEntity<List<Sales>> getSalesByFarm(@PathVariable Long farmId) {
        try {
            List<Sales> sales = salesService.getSalesByFarmId(farmId);
            if (!sales.isEmpty()) {
                return ResponseEntity.ok(sales);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
