package proyect.Farm.business;

public interface ISimulation {
    void startSimulation(Long farmId, Integer speed);
    void stopSimulation(Long farmId);
    void pauseSimulation(Long farmId);
    void resumeSimulation(Long farmId);
}
