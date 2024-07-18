package proyect.Farm.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@Entity
@Table(name = "eggs")
@Data
@PropertySource("farm.properties")
public class Egg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double price;
    private Integer ageInDays;
    @Value("${EGGS.DAYS.TO.HATCH}")
    private Integer daysToHatch;

    public Egg(Integer ageInDays, Double price) {
        this.ageInDays = ageInDays;
        this.price = price;
    }

    @ManyToOne
    @JoinColumn(name = "farm_id")
    private Farm farm;

    public Egg() {
    }
}