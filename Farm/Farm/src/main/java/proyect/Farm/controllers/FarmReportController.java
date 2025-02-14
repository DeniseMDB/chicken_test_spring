package proyect.Farm.controllers;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import proyect.Farm.entities.FarmReport;
import proyect.Farm.services.FarmReportService;

import java.io.IOException;

@RestController
@RequestMapping("api/farm/report")
@Tag(name = "Farm Report Controller", description = "Controls to manage farm reports")
@SecurityRequirement(name = "basicAuth")
public class FarmReportController {
    @Autowired
    private FarmReportService farmReportService;

    @Operation(summary = "Get farm report by ID", description = "Retrieve the farm report for a specific farm by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Farm report retrieved successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FarmReport.class)) }),
            @ApiResponse(responseCode = "404", description = "Farm report not found for the specified ID",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<FarmReport> getFarmReport(@PathVariable Long id){
        try {
            FarmReport farmReport = farmReportService.generateFarmReport(id);
            return ResponseEntity.ok(farmReport);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

@Operation(summary = "Generate CSV farm report by ID", description = "Generate and download the farm report in CSV format for a specific farm by its ID")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "CSV farm report generated successfully",
                content = { @Content(mediaType = "text/csv") }),
        @ApiResponse(responseCode = "404", description = "Farm report not found for the specified ID",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content) })
@GetMapping("/{id}/csv")
public void generateCsvFarmReport(@PathVariable Long id, HttpServletResponse response) {
    try {
        farmReportService.writeFarmReportToCSV(id, response);
    } catch (RuntimeException e) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
    } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
}
