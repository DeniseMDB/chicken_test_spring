package proyect.Farm.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import proyect.Farm.entities.Chicken;
import proyect.Farm.entities.Farm;
import proyect.Farm.repositories.ChickenRepository;
import proyect.Farm.repositories.FarmRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
@PropertySource("farm.properties")
public class ChickenService {

    @Autowired
    private ChickenRepository chickenRepository;
    @Autowired
    private FarmRepository farmRepository;

    @Value("${CHICKENS.MIN.DAYS.TO.LIVE}")
    private int minDaysToLive;

    @Value("${CHICKENS.MAX.DAYS.TO.LIVE}")
    private int maxDaysToLive;

    private static final Random random = new Random();

    public List<Chicken> findAll() {
        return (List<Chicken>) chickenRepository.findAll();
    }

    public List<Chicken> findChickensByStatus(Long farmId, Boolean isAlive) {
        return (List<Chicken>) chickenRepository.findChickensByStatus(farmId, isAlive);
    }

    public synchronized Chicken save(Chicken chicken, Long farmId) {
        if (chicken.getDaysToLive() == null) {
            chicken.setDaysToLive(minDaysToLive + random.nextInt(maxDaysToLive - minDaysToLive + 1));
        }
        if(chicken.getIsAlive() == null){
            chicken.setIsAlive(true);
        }
        if(chicken.getDaysUntilNextEgg() == null){
            if(chicken.getAgeInDays() > 20){
                chicken.setDaysUntilNextEgg(1);
            }else {
                chicken.setDaysUntilNextEgg(20 - chicken.getAgeInDays());
            }
        }
        Optional<Farm> oPfarm = farmRepository.findById(farmId);
        if (oPfarm.isPresent()) {
            Farm farm = oPfarm.get();
            chicken.setFarm(farm);
        }else throw new RuntimeException("Error de farm");
        return chickenRepository.save(chicken);
    }
    public void delete(Chicken chicken, Long id) {
        Optional<Farm> oPfarm = farmRepository.findById(id);
        if (oPfarm.isPresent()) {
            Farm farm = oPfarm.get();
            List<Chicken> chickens = farm.getChickens();
            chickens.remove(chicken);
            farm.setChickens(chickens);
            farmRepository.save(farm);
            chickenRepository.deleteById(chicken.getId());
        } else throw new RuntimeException("Error de farm");
    }
}
