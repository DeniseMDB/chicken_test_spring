package proyect.Farm.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Represents a chicken in the farm")
public class Chicken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the chicken", example = "1")
    private Long id;

    @Schema(description = "Age of the chicken in days", example = "10")
    private Integer ageInDays;

    @Schema(description = "Number of days the chicken will live", example = "45")
    private Integer daysToLive;

    @Value("{$CHICKENS.EGGS.PER.DAY}")
    @Schema(description = "Days until the chicken lays the next egg", example = "1")
    private Integer daysUntilNextEgg;

    @Schema(description = "Indicates if the chicken is alive", example = "true")
    private Boolean isAlive;

    @Schema(description = "Type of chicken", example = "born")
    private String kindOfChicken;

    @Schema(description = "Price of the chicken", example = "15.0")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "farm_id")
    @Schema(description = "The farm to which the chicken belongs")
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

