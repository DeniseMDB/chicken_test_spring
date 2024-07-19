package proyect.Farm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import proyect.Farm.entities.Chicken;
import proyect.Farm.entities.Farm;
import proyect.Farm.repositories.ChickenRepository;
import proyect.Farm.repositories.FarmRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
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

    public List<Chicken> findAllAlive() {
        return (List<Chicken>) chickenRepository.findChickensAlive(true);
    }

    public Chicken save(Chicken chicken, Long farmId) {
        if (chicken.getDaysToLive() == null) {
            System.out.println(minDaysToLive);
            chicken.setDaysToLive(minDaysToLive + random.nextInt(maxDaysToLive - minDaysToLive + 1));
        }
        Optional<Farm> oPfarm = farmRepository.findById(farmId);
        if (oPfarm.isPresent()) {
            Farm farm = oPfarm.get();
            chicken.setFarm(farm);
        }else throw new RuntimeException("Error de farm");
        return chickenRepository.save(chicken);
    }

    private int initializeDaysToLive() {
        return minDaysToLive + random.nextInt(maxDaysToLive - minDaysToLive + 1);
    }
}
