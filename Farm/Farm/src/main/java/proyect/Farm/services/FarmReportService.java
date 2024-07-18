package proyect.Farm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyect.Farm.entities.Farm;
import proyect.Farm.entities.FarmReport;
import proyect.Farm.entities.Sales;
import proyect.Farm.repositories.FarmRepository;
import proyect.Farm.repositories.SalesRepository;
import proyect.Farm.repositories.SalesRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FarmReportService {
    @Autowired
    private FarmRepository farmRepository;
    @Autowired
    private SalesRepository salesRepository;

    public FarmReport generateFarmReport(Long farmId) {
        Optional<Farm> optionalFarm = farmRepository.findById(farmId);
        if (optionalFarm.isPresent()) {
            Farm farm = optionalFarm.get();
            List<Sales> sales = salesRepository.findByFarm_Id(farm.getId());

            int totalEggsSold = 0;
            int totalChickensSold = 0;
            double totalEggsRevenue = 0;
            double totalChickensRevenue = 0;

            for (Sales sale : sales) {
                if (sale.getItemType().equals("egg")) {
                    totalEggsSold += sale.getQuantity();
                    totalEggsRevenue += sale.getPrice();
                } else if (sale.getItemType().equals("chicken")) {
                    totalChickensSold += sale.getQuantity();
                    totalChickensRevenue += sale.getPrice();
                }
            }

            return new FarmReport(
                    farm.getMoney(),
                    farm.getChickens().size(),
                    farm.getEggs().size(),
                    totalEggsSold,
                    totalChickensSold,
                    totalEggsRevenue,
                    totalChickensRevenue,
                    farm.getMaxEggs(),
                    farm.getMaxChickens(),
                    farm.getDaysInBusiness()
            );
        } else {
            throw new RuntimeException("Farm does not exist");
        }
    }
}