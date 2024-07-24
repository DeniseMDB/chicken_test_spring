package proyect.Farm.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import proyect.Farm.entities.FarmReport;
import proyect.Farm.services.FarmReportService;

import java.util.NoSuchElementException;

@Controller
public class FarmReportControllerWeb {

    @Autowired
    private FarmReportService farmReportService;

    @GetMapping("/web/farm/{farmId}")
    public String getFarmStatus(@PathVariable Long farmId, Model model) {
        try {
            FarmReport farmReport = farmReportService.generateFarmReport(farmId);
            model.addAttribute("farm", farmReport);
            return "farmStatus";
        } catch (NoSuchElementException e) {
            return "error";
        }
    }
}
