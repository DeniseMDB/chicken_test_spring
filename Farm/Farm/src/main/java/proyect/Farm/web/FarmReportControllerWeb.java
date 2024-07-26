package proyect.Farm.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import proyect.Farm.entities.Farm;
import proyect.Farm.entities.FarmReport;
import proyect.Farm.services.FarmReportService;
import proyect.Farm.services.FarmService;

import java.util.NoSuchElementException;

@Controller
public class FarmReportControllerWeb {

    @Autowired
    private FarmReportService farmReportService;
    @Autowired
    private FarmService farmService;

    @GetMapping("/web/farm/{farmId}")
    public String getFarmStatus(@PathVariable Long farmId, Model model) {
        try {
            FarmReport farmReport = farmReportService.generateFarmReport(farmId);
            Farm farm = farmService.findById(farmId);
            model.addAttribute("farm", farmReport);
            model.addAttribute("farmid", farm);
            return "farmStatus";
        } catch (NoSuchElementException e) {
            return "error";
        }
    }
}
