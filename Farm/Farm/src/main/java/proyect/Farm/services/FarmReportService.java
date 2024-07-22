package proyect.Farm.services;

import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import proyect.Farm.entities.Farm;
import proyect.Farm.entities.FarmReport;
import proyect.Farm.entities.Sales;
import proyect.Farm.repositories.FarmReportRepository;
import proyect.Farm.repositories.FarmRepository;
import proyect.Farm.repositories.SalesRepository;
import proyect.Farm.repositories.SalesRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@PropertySource("farm.properties")
public class FarmReportService {
    @Autowired
    private FarmRepository farmRepository;
    @Autowired
    private SalesRepository salesRepository;
    @Autowired
    private FarmReportRepository farmReportRepository;
    @Value("${FILE.PATH.CSV}")
    private String localPath;

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
            throw new RuntimeException("Farm does not exist");
        }
    }

        public void writeFarmReportToCSV(Long farmId,String filePath) {
            FarmReport farmReport = generateFarmReport(farmId);

            try (CSVWriter writer = new CSVWriter(new FileWriter(this.localPath + filePath))) {
                String[] header = { "Money", "Chickens", "Eggs", "Total Eggs Sold", "Total Chickens Sold",
                        "Total Eggs Revenue", "Total Chickens Revenue", "Eggs Capacity",
                        "Chickens Capacity", "Days in Business" };
                writer.writeNext(header);

                String[] data = {
                        String.valueOf(farmReport.getMoney()),
                        String.valueOf(farmReport.getCurrentChickens()),
                        String.valueOf(farmReport.getCurrentEggs()),
                        String.valueOf(farmReport.getTotalEggsSold()),
                        String.valueOf(farmReport.getTotalChickensSold()),
                        String.valueOf(farmReport.getTotalEggsRevenue()),
                        String.valueOf(farmReport.getTotalChickensRevenue()),
                        String.valueOf(farmReport.getEggsCapacity()),
                        String.valueOf(farmReport.getChickensCapacity()),
                        String.valueOf(farmReport.getDaysInBusiness())
                };

                writer.writeNext(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
