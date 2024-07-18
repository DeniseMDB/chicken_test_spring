package proyect.Farm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyect.Farm.entities.*;
import proyect.Farm.repositories.*;

import java.util.Optional;

@Service
public class FarmService implements Runnable{
    @Autowired
    private FarmRepository farmRepository;
    @Autowired
    private ChickenRepository chickenRepository;
    @Autowired
    private EggRepository eggRepository;
    @Autowired
    private SalesRepository saleRepository;

    private boolean running = true;
    private final Object lock = new Object();

    public void startSimulation() {
        Thread simulationThread = new Thread(this);
        simulationThread.start();
    }

    public void stopSimulation() {
        running = false;
    }

    public void pauseSimulation() {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void resumeSimulation() {
        synchronized (lock) {
            lock.notify();
        }
    }
//agregar parametro de tiempo
    @Override
    public void run() {
        while (running) {
            simulateDay();
            try {
                Thread.sleep(30000); // Se pausa 30 segundos
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            synchronized (lock) {
                // revisa si esta pausado
            }
        }
    }

    public void simulateDay() {
        ageEggs();
        ageChickens();
        Optional<Farm> optionalFarm = farmRepository.findById(1L);
        if (optionalFarm.isPresent()) {
            Farm farm = optionalFarm.get();
            farm.setDaysInBusiness(farm.getDaysInBusiness() + 1);
        }
    }

    public void buyEggs(Integer amount, Double pricePerEgg){
        Double totalPrice = amount*pricePerEgg;
        Optional<Farm> optionalFarm = farmRepository.findById(1L);
        if (optionalFarm.isPresent()){
            Farm farm = optionalFarm.get();
            if(totalPrice < farm.getMoney()){
                farm.setMoney(farm.getMoney()-totalPrice); // asi o con queries?
                for(int i = 0; i < amount; i++){
                    Egg egg= new Egg(0,0.75);
                    egg.setFarm(farm);
                    eggRepository.save(egg);
                    farm.getEggs().add(egg);
                }
                farmRepository.save(farm);
            }else{
                throw new RuntimeException("Not enough money to buy eggs");//puedo crear excepciones personalizadas
            }
        }else{
            throw new RuntimeException("Farm does not exist");
        }
    }

    public void buyChickens(Integer amount, Double pricePerChicken, Integer chickenAgeInDays){
        Double totalPrice = amount*pricePerChicken;
        Optional<Farm> optionalFarm = farmRepository.findById(1L);
        if (optionalFarm.isPresent()){
            Farm farm = optionalFarm.get();
            if(totalPrice < farm.getMoney()){
                farm.setMoney(farm.getMoney()-totalPrice); // asi o con queries?
                for(int i = 0; i < amount; i++){
                    Chicken chicken= new Chicken(chickenAgeInDays,1,true,"bought",55.0);
                    chicken.setFarm(farm);
                    chickenRepository.save(chicken);
                    farm.getChickens().add(chicken);
                }
                farmRepository.save(farm);
            }else{
                throw new RuntimeException("Not enough money to buy chickens");
            }
        }else{
            throw new RuntimeException("Farm does not exist");
        }
    }

    public void sellEggs(Integer amount) {
        Optional<Farm> optionalFarm = farmRepository.findById(1L);
        if (optionalFarm.isPresent()) {
            Farm farm = optionalFarm.get();
            if (amount <= farm.getEggs().size()) {
                Double totalSale = 0.0;
                for (int i = 0; i < amount; i++) {
                    Egg egg = farm.getEggs().get(0);
                    totalSale += egg.getPrice();
                    eggRepository.delete(egg);
                    farm.getEggs().remove(egg);
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

    public void sellChickens(Integer amount){
        Optional<Farm> optionalFarm = farmRepository.findById(1L);
        if (optionalFarm.isPresent()) {
            Farm farm = optionalFarm.get();
            if (amount <= farm.getChickens().size()) {
                double totalSale = 0;
                for (int i = 0; i < amount; i++) {
                    Chicken chicken = farm.getChickens().get(0);
                    totalSale += chicken.getPrice();
                    chickenRepository.delete(chicken);
                    farm.getChickens().remove(chicken);
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

    public void ageEggs(){
        Iterable<Egg> eggsList = eggRepository.findAll();
        for(Egg egg : eggsList){
            egg.setAgeInDays(egg.getAgeInDays()+1);
            if(egg.getDaysToHatch() >=egg.getAgeInDays()){
                hatchEggs(egg);
            }else{
                eggRepository.save(egg);
            }
        }
    }

    public void ageChickens(){
        Iterable<Chicken> chickenList = chickenRepository.findAll();
        for(Chicken chicken : chickenList){
            chicken.setAgeInDays(chicken.getAgeInDays()+1);
            if(chicken.getAgeInDays() >= chicken.getDaysToLive()){
                chicken.setIsAlive(false);
                chickenRepository.delete(chicken);
            }else{
                chickenRepository.save(chicken);
            }
        }
    }

    public void hatchEggs(Egg egg) {
        Optional<Farm> optionalFarm = farmRepository.findById(egg.getFarm().getId());
        if (optionalFarm.isPresent()) {
            Farm farm = optionalFarm.get();
            eggRepository.delete(egg);
            Chicken chicken = new Chicken(0, 20, true,"born",45.0);
            chicken.setFarm(farm);
            chickenRepository.save(chicken);
            farm.getChickens().add(chicken);
            farm.getEggs().remove(egg);
            farmRepository.save(farm);
        }
    }
}
