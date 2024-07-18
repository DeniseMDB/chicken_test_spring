package proyect.Farm.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "reports")
@Data
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
    private Integer maxEggs;
    private Integer maxChickens;
    private Integer daysInBusiness;

    public FarmReport(Double money, Integer currentChickens, Integer currentEggs, Integer totalEggsSold, Integer totalChickensSold, Double totalEggsRevenue, Double totalChickensRevenue, Integer maxEggs, Integer maxChickens, Integer daysInBusiness) {
        this.money = money;
        this.currentChickens = currentChickens;
        this.currentEggs = currentEggs;
        this.totalEggsSold = totalEggsSold;
        this.totalChickensSold = totalChickensSold;
        this.totalEggsRevenue = totalEggsRevenue;
        this.totalChickensRevenue = totalChickensRevenue;
        this.maxEggs = maxEggs;
        this.maxChickens = maxChickens;
        this.daysInBusiness = daysInBusiness;
    }

    public FarmReport() {
    }
}