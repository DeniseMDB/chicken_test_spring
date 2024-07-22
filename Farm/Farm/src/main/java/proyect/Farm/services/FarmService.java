package proyect.Farm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proyect.Farm.entities.*;
import proyect.Farm.repositories.*;

import java.util.*;

@Service
@Transactional
public class FarmService {

    @Autowired
    private FarmRepository farmRepository;
    @Autowired
    private ChickenRepository chickenRepository;
    @Autowired
    private EggRepository eggRepository;
    @Autowired
    private SalesRepository saleRepository;
    @Autowired
    private ChickenService chickenService;
    @Autowired
    private EggService eggService;

    public void simulateDay(Long farmId) {
        Optional<Farm> optionalFarm = farmRepository.findById(farmId);
        if (optionalFarm.isPresent()) {
            Farm farm = optionalFarm.get();
            Integer chickensInStock = farm.getChickens().size(); // Inicializa la colección LAZY
            Integer eggsInStock = farm.getEggs().size();// Inicializa la colección LAZY
            if (eggsInStock >= farm.getMaxEggs()){ //control de stock de huevos
                double calculatedAmount = eggsInStock * 0.20;
                Integer eggsToSell = (int) Math.round(calculatedAmount);
                sellEggs(eggsToSell,farm.getId(),0.75);
                System.out.println("Se han vendido "+ eggsToSell+" huevos por alcanzar el limite de stock en granja");
            }
            if (chickensInStock >= farm.getMaxChickens()){ //control de stock de gallinas
                double calculatedAmount = chickensInStock * 0.20;
                Integer chickensToSell = (int) Math.round(calculatedAmount);
                sellEggs(chickensToSell,farm.getId(),0.75);
                System.out.println("Se han vendido "+ chickensToSell+" gallinas por alcanzar el limite de stock en granja");
            }
            List<Chicken> deadChickens = chickenRepository.findChickensByStatus(farm.getId(), false);
            if (!deadChickens.isEmpty()) { //desecha gallinas muertas
                System.out.println("Se desecharon "+ deadChickens.size()+" gallinas muertas.");
                for (Chicken chicken : deadChickens) {
                    chickenService.delete(chicken,farm.getId());
                }
            }
            ageChickens(farm);
            ageEggs(farm);
            farm.setDaysInBusiness(farm.getDaysInBusiness() + 1);
            farmRepository.save(farm);
            System.out.println("A full day has passed.");
        }
    }

    public Farm saveFarm(Farm farm) {
        return farmRepository.save(farm);
    }

    public void buyEggs(Integer amount, Double pricePerEgg, Long id) {
        Double totalPrice = amount * pricePerEgg;
        Optional<Farm> optionalFarm = farmRepository.findById(id);
        if (optionalFarm.isPresent()) {
            Farm farm = optionalFarm.get();
            if (totalPrice < farm.getMoney()) {
                farm.setMoney(farm.getMoney() - totalPrice);
                for (int i = 0; i < amount; i++) {
                    Egg egg = new Egg(null, 0.75, null, "bought");
                    eggService.save(egg, id);
                    farm.getEggs().add(egg);
                }
                farmRepository.save(farm);
            } else {
                throw new RuntimeException("Not enough money to buy eggs");
            }
        } else {
            throw new RuntimeException("Farm does not exist");
        }
    }

    public void buyChickens(Integer amount, Double pricePerChicken, Integer chickenAgeInDays, Long farmId) {
        Double totalPrice = amount * pricePerChicken;
        Optional<Farm> optionalFarm = farmRepository.findById(farmId);
        if (optionalFarm.isPresent()) {
            Farm farm = optionalFarm.get();
            if (totalPrice < farm.getMoney()) {
                farm.setMoney(farm.getMoney() - totalPrice);
                for (int i = 0; i < amount; i++) {
                    Chicken chicken = new Chicken(chickenAgeInDays, null, true, null, "bought", 55.0);
                    chickenService.save(chicken, farm.getId());
                    farm.getChickens().add(chicken);
                }
                farmRepository.save(farm);
            } else {
                throw new RuntimeException("Not enough money to buy chickens");
            }
        } else {
            throw new RuntimeException("Farm does not exist");
        }
    }

    public void sellEggs(Integer amount, Long farmId, Double discount) {
        Optional<Farm> optionalFarm = farmRepository.findById(farmId);
        Double discountToApply = 0.0;
        if (discount == null) {
            discountToApply = 1.00;
        }else{
            discountToApply = discount;
        }
        if (optionalFarm.isPresent()) {
            Farm farm = optionalFarm.get();
            if (amount <= farm.getEggs().size()) {
                Double totalSale = 0.0;
                List<Egg> eggs = farm.getEggs();
                for (int i = 0; i < amount; i++) {
                    Egg egg = farm.getEggs().get(0);
                    totalSale += egg.getPrice()*discountToApply;
                    eggRepository.delete(egg);
                    eggs.remove(egg);
                    farm.setEggs(eggs);
                }
                farm.setMoney(farm.getMoney() + totalSale);
                Sales sale = new Sales("egg", amount, totalSale);
                sale.setFarm(farm);
                saleRepository.save(sale);
                farm.getSales().add(sale);
                farmRepository.save(farm);
            } else {
                throw new RuntimeException("Not enough amount of eggs to sell");
            }
        } else {
            throw new RuntimeException("Farm does not exist");
        }
    }

    public void sellChickens(Integer amount, Long farmId, Double discount) {
        Optional<Farm> optionalFarm = farmRepository.findById(farmId);
        Double discountToApply = 0.0;
        if (discount == null) {
            discountToApply = 1.00;
        }else{
            discountToApply = discount;
        }
        if (optionalFarm.isPresent()) {
            Farm farm = optionalFarm.get();
            if (amount <= farm.getChickens().size()) {
                double totalSale = 0;
                List<Chicken> chickens = farm.getChickens();
                for (int i = 0; i < amount; i++) {
                    Chicken chicken = chickens.get(0);
                    totalSale += chicken.getPrice()*discountToApply;
                    chickenRepository.delete(chicken);
                    chickens.remove(chicken);
                    farm.setChickens(chickens);
                }
                farm.setMoney(farm.getMoney() + totalSale);
                Sales sale = new Sales("chicken", amount, totalSale);
                sale.setFarm(farm);
                saleRepository.save(sale);
                farm.getSales().add(sale);
                farmRepository.save(farm);
            } else {
                throw new RuntimeException("Not enough amount of chickens to sell");
            }
        } else {
            throw new RuntimeException("Farm does not exist");
        }
    }

    public void ageEggs(Farm farm) {
        List<Egg> eggsList = new ArrayList<>(farm.getEggs());
        for (Egg egg : eggsList) {
            egg.setAgeInDays(egg.getAgeInDays() + 1);
            egg.setDaysToHatch(egg.getDaysToHatch() - 1);
            if (egg.getDaysToHatch() <= 0) {
                hatchEggs(egg, farm);
            } else {
                eggRepository.save(egg);
            }
        }
        farmRepository.save(farm);
    }

    public void ageChickens(Farm farm) {
        List<Chicken> chickenList = new ArrayList<>(farm.getChickens());
        List<Chicken> chickensToRemove = new ArrayList<>();
        for (Chicken chicken : chickenList) {
            if (chicken.getDaysUntilNextEgg() == 0) {
                Egg egg = new Egg(0, 0.50, 21, "hatched");
                eggService.save(egg, farm.getId());
                farm.getEggs().add(egg);
                chicken.setDaysUntilNextEgg(2);
            }
            chicken.setAgeInDays(chicken.getAgeInDays() + 1);
            chicken.setDaysToLive(chicken.getDaysToLive() - 1);
            chicken.setDaysUntilNextEgg(chicken.getDaysUntilNextEgg() - 1);
            if (chicken.getDaysToLive() <= 0) {
                chicken.setIsAlive(false);
                chickensToRemove.add(chicken);
            } else {
                chickenRepository.save(chicken);
            }
        }
        chickenList.removeAll(chickensToRemove);
        farm.setChickens(chickenList);
        farmRepository.save(farm);
    }

    public void hatchEggs(Egg egg, Farm farm) {
        eggRepository.delete(egg);
        Chicken chicken = new Chicken(0, 20, true, null, "born", 45.0);
        chickenService.save(chicken, farm.getId());
        farm.getChickens().add(chicken);
        farm.getEggs().remove(egg);
        farmRepository.save(farm);
    }

    public Farm findById(Long id) {
        Optional<Farm> optionalFarm = farmRepository.findById(id);
        if (optionalFarm.isPresent()) {
            return optionalFarm.get();
        } else {
            throw new RuntimeException("Farm does not exist");
        }
    }

}
