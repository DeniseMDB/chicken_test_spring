package proyect.Farm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import proyect.Farm.entities.Egg;
import proyect.Farm.entities.Farm;
import proyect.Farm.repositories.EggRepository;
import proyect.Farm.repositories.FarmRepository;

import java.util.List;
import java.util.Optional;

@Service
@PropertySource("farm.properties")
public class EggService {
    @Autowired
    private EggRepository eggRepository;
    @Autowired
    private FarmRepository farmRepository;
    @Value("${EGGS.DAYS.TO.HATCH}")
    private Integer daysToHatch;

    public List<Egg> findAll(){
        return (List<Egg>) eggRepository.findAll();
    }

    public Egg save(Egg egg, Long farmId){
        if(egg.getDaysToHatch() == null){
            egg.setDaysToHatch(daysToHatch);
        }
        Optional<Farm> oPfarm = farmRepository.findById(farmId);
        if (oPfarm.isPresent()) {
            Farm farm = oPfarm.get();
            egg.setFarm(farm);
        }else throw new RuntimeException("Error de farm");
        return eggRepository.save(egg);
    }


}
