package proyect.Farm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import proyect.Farm.entities.Egg;
import proyect.Farm.entities.Farm;
import proyect.Farm.exceptions.FarmNotFoundException;
import proyect.Farm.repositories.EggRepository;
import proyect.Farm.repositories.FarmRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@PropertySource("farm.properties")
public class EggService {
    @Autowired
    private EggRepository eggRepository;
    @Autowired
    private FarmRepository farmRepository;
    @Value("${EGGS.DAYS.TO.HATCH}")
    private Integer daysToHatchFromBirth;

    Random random = new Random();
    int randomizedDaysToHatch = 0;

    public List<Egg> findAll(){
        return (List<Egg>) eggRepository.findAll();
    }

    public Egg save(Egg egg, Long farmId){
        if(egg.getDaysToHatch() == null){
            this.randomizedDaysToHatch = (random.nextInt(daysToHatchFromBirth) + 1);
            egg.setDaysToHatch(randomizedDaysToHatch);
        }
        if(egg.getAgeInDays() == null){
            egg.setAgeInDays(daysToHatchFromBirth - randomizedDaysToHatch);
        }
        Optional<Farm> oPfarm = farmRepository.findById(farmId);
        if (oPfarm.isPresent()) {
            Farm farm = oPfarm.get();
            egg.setFarm(farm);
        }else throw new FarmNotFoundException("Can't find Farm with ID: "+farmId);
        return eggRepository.save(egg);
    }


}
