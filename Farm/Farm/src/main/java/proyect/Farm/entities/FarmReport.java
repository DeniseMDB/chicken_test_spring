package proyect.Farm.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "reports")
@Data
@NoArgsConstructor
public class FarmReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double money;
    private Integer currentChickens;
    private Integer currentEggs;
    private Integer totalEggsSold;
    private Integer totalChickensSold;
    private Double totalEggsRevenue;
    private Double totalChickensRevenue;
    private Integer eggsCapacity;
    private Integer chickensCapacity;
    private Integer daysInBusiness;

    public FarmReport(Double money, Integer currentChickens, Integer currentEggs, Integer totalEggsSold, Integer totalChickensSold, Double totalEggsRevenue, Double totalChickensRevenue, Integer eggsCapacity, Integer chickensCapacity, Integer daysInBusiness) {
        this.money = money;
        this.currentChickens = currentChickens;
        this.currentEggs = currentEggs;
        this.totalEggsSold = totalEggsSold;
        this.totalChickensSold = totalChickensSold;
        this.totalEggsRevenue = totalEggsRevenue;
        this.totalChickensRevenue = totalChickensRevenue;
        this.eggsCapacity = eggsCapacity;
        this.chickensCapacity =chickensCapacity;
        this.daysInBusiness = daysInBusiness;
    }
}