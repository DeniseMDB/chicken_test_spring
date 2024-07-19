package proyect.Farm.controllers;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import proyect.Farm.entities.Sales;
import proyect.Farm.services.FarmService;
import proyect.Farm.services.SalesService;

import java.util.List;

@RestController
@RequestMapping("api/sales")
public class SalesController {
    @Autowired
    SalesService salesService;

    @GetMapping("/{farmId}")
    public ResponseEntity<List<Sales>> getSalesByFarm(@PathVariable Long farmId) {
        List<Sales> sales = salesService.getSalesByFarmId(farmId);
        if (sales != null) {
            return ResponseEntity.ok(sales);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
