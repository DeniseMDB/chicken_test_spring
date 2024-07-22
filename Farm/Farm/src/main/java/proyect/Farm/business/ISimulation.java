package proyect.Farm.business;

public interface ISimulation {
    public void startSimulation(Long farmId, Integer speed);
    public void stopSimulation(Long farmId);
    public void pauseSimulation(Long farmId);
    public void resumeSimulation(Long farmId);
}
