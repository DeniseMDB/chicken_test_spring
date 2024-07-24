package proyect.Farm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyect.Farm.business.ISimulation;

@Service
public class SimulationService implements Runnable, ISimulation {

    @Autowired
    private FarmService farmService;

    private boolean running = true;
    private final Object lock = new Object();
    private Long currentFarmId;
    private Integer speedSimulation;
    private final int millis= 1000;

    public void startSimulation(Long farmId, Integer speed) {
        this.currentFarmId = farmId;
        this.speedSimulation = speed;
        Thread simulationThread = new Thread(this);
        simulationThread.start();
    }

    public void stopSimulation(Long farmId) {
        if (farmId.equals(this.currentFarmId)) {
            running = false;
        }
    }

    public void pauseSimulation(Long farmId) {
        if (farmId.equals(this.currentFarmId)) {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public void resumeSimulation(Long farmId) {
        if (farmId.equals(this.currentFarmId)) {
            synchronized (lock) {
                lock.notify();
            }
        }
    }

    @Override
    public void run() {
        while (running) {
            if (currentFarmId != null) {
                farmService.simulateDay(currentFarmId);
            }
            try {
                Thread.sleep(speedSimulation*millis); //
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            synchronized (lock) {
                // revisa si esta pausado
            }
        }
    }
}
