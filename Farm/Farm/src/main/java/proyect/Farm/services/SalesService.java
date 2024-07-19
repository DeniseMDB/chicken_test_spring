package proyect.Farm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyect.Farm.entities.Sales;
import proyect.Farm.repositories.SalesRepository;

import java.util.List;

@Service
public class SalesService {
    @Autowired
    SalesRepository salesRepository;

    public List<Sales> getSalesByFarmId(Long farmId) {
        return salesRepository.findByFarm_Id(farmId);
    }
}
