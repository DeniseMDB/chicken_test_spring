package proyect.Farm.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import java.util.Random;

@Entity
@Table(name = "chickens")
@Data
@PropertySource("farm.properties")
@JsonIgnoreProperties({"farm"})
public class Chicken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer ageInDays;
    private Integer daysToLive;
    @Value("{$CHICKENS.EGGS.PER.DAY}")
    private Integer daysUntilNextEgg;
    private Boolean isAlive;
    private String kindOfChicken;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "farm_id")
    private Farm farm;

    private static final Random random = new Random();

    public Chicken(Integer ageInDays, Integer daysUntilNextEgg, Boolean isAlive,Integer daysToLive, String kindOfChicken, Double price) {
        this.ageInDays = ageInDays;
        this.daysUntilNextEgg = daysUntilNextEgg;
        this.isAlive = isAlive;
        this.daysToLive= daysToLive;
        this.kindOfChicken= kindOfChicken;
        this.price= price;
    }


    public Chicken() {
    }
}
//h2 resource db sql inicializa
