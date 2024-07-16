package proyect.Farm.services;

import org.springframework.stereotype.Service;
import proyect.Farm.entities.Chicken;
import proyect.Farm.repositories.ChickenRepository;

import java.util.List;

@Service
public class ChickenService {
    ChickenRepository chickenRepository;

    public List<Chicken> findAll(){
        return (List<Chicken>) chickenRepository.findAll();
    }
    public List<Chicken> findAllAlive(){
        return (List<Chicken>) chickenRepository.findChickensAlive(true);
    }
}
