package proyect.Farm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import proyect.Farm.entities.FarmReport;
import proyect.Farm.services.FarmReportService;

@RestController
@RequestMapping("api/farm/report")
public class FarmReportController {
    @Autowired
    private FarmReportService farmReportService;

    @GetMapping("/{id}")
    public ResponseEntity<FarmReport> getFarmReport(@PathVariable Long id){
        try{
            FarmReport farmReport = farmReportService.generateFarmReport(id);
            return ResponseEntity.ok(farmReport);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
