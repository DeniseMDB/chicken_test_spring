package proyect.Farm.services;

import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import proyect.Farm.entities.Farm;
import proyect.Farm.entities.FarmReport;
import proyect.Farm.entities.Sales;
import proyect.Farm.exceptions.FarmNotFoundException;
import proyect.Farm.repositories.FarmReportRepository;
import proyect.Farm.repositories.FarmRepository;
import proyect.Farm.repositories.SalesRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FarmReportService {
    @Autowired
    private FarmRepository farmRepository;
    @Autowired
    private SalesRepository salesRepository;
    @Autowired
    private FarmReportRepository farmReportRepository;

    public FarmReport generateFarmReport(Long farmId) {
        Optional<Farm> optionalFarm = farmRepository.findById(farmId);
        if (optionalFarm.isPresent()) {
            Farm farm = optionalFarm.get();
            List<Sales> sales = salesRepository.findByFarm_Id(farm.getId());
            int totalEggsSold = 0;
            int totalChickensSold = 0;
            double totalEggsRevenue = 0;
            double totalChickensRevenue = 0;
            int chickensCapacity = farm.getMaxChickens() - farm.getChickens().size();
            int eggsCapacity = farm.getMaxEggs() - farm.getEggs().size();
            for (Sales sale : sales) {
                if (sale.getItemType().equals("egg")) {
                    totalEggsSold += sale.getQuantity();
                    totalEggsRevenue += sale.getPrice();
                } else if (sale.getItemType().equals("chicken")) {
                    totalChickensSold += sale.getQuantity();
                    totalChickensRevenue += sale.getPrice();
                }
            }

            FarmReport farmReport = new FarmReport(
                    farm.getMoney(),
                    farm.getChickens().size(),
                    farm.getEggs().size(),
                    totalEggsSold,
                    totalChickensSold,
                    totalEggsRevenue,
                    totalChickensRevenue,
                    eggsCapacity,
                    chickensCapacity,
                    farm.getDaysInBusiness());
            return farmReportRepository.save(farmReport);
        } else {
            throw new FarmNotFoundException("Can't find Farm with ID: "+farmId);
        }
    }

    public void writeFarmReportToCSV(Long farmId, HttpServletResponse response) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        FarmReport farmReport = generateFarmReport(farmId);

        List<FarmReport> farmReportList = new ArrayList<>();
        farmReportList.add(farmReport);

        String[] header = { "Money", "Chickens", "Eggs", "Total Eggs Sold", "Total Chickens Sold",
                "Total Eggs Revenue", "Total Chickens Revenue", "Eggs Capacity",
                "Chickens Capacity", "Days in Business" };

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"farm_report_" + farmId + ".csv\"");

        try (CSVWriter writer = new CSVWriter(response.getWriter())) {
            writer.writeNext(header);

            for (FarmReport report : farmReportList) {
                String[] data = {
                        String.valueOf(report.getMoney()),
                        String.valueOf(report.getCurrentChickens()),
                        String.valueOf(report.getCurrentEggs()),
                        String.valueOf(report.getTotalEggsSold()),
                        String.valueOf(report.getTotalChickensSold()),
                        String.valueOf(report.getTotalEggsRevenue()),
                        String.valueOf(report.getTotalChickensRevenue()),
                        String.valueOf(report.getEggsCapacity()),
                        String.valueOf(report.getChickensCapacity()),
                        String.valueOf(report.getDaysInBusiness())
                };
                writer.writeNext(data);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while writing CSV", e);
        }
    }
    }
