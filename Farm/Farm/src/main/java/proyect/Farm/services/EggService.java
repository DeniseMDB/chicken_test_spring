package proyect.Farm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyect.Farm.entities.Egg;
import proyect.Farm.repositories.EggRepository;

import java.util.List;

@Service
public class EggService {
    @Autowired
    private EggRepository eggRepository;

    public List<Egg> findAll(){
        return (List<Egg>) eggRepository.findAll();
    }

    public Egg save(Egg egg){
        return eggRepository.save(egg);
    }


}
