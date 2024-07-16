package proyect.Farm.services;

import org.springframework.stereotype.Service;
import proyect.Farm.repositories.ChickenRepository;
import proyect.Farm.repositories.EggRepository;
import proyect.Farm.repositories.FarmRepository;

@Service
public class FarmService {
    FarmRepository farmRepository;
    ChickenRepository chickenRepository;
    EggRepository eggRepository;

    public void buyEggs(Integer amount, Double pricePerEgg){

    }
    public void buyChickens(Integer amount, Double pricePerChicken){

    }
    public void sellEggs(Integer amount){

    }
    public void sellChickens(Integer amount){

    }
    public void hatchEggs(){

    }
    public void ageChickens(){

    }

}
