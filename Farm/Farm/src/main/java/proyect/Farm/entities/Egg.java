package proyect.Farm.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@Entity
@Table(name = "eggs")
@Data
@PropertySource("farm.properties")
@JsonIgnoreProperties({"farm"})
@NoArgsConstructor
@Schema(description = "Represents an egg in the farm")
public class Egg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the egg", example = "1")
    private Long id;

    @Schema(description = "Price of the egg", example = "0.5")
    private Double price;

    @Schema(description = "Age of the egg in days", example = "3")
    private Integer ageInDays;

    @Value("${EGGS.DAYS.TO.HATCH}")
    @Schema(description = "Days until the egg hatches", example = "21")
    private Integer daysToHatch;

    @Schema(description = "Indicates if the egg was hatched or bought", example = "hatched")
    private String hatchedOrBought;

    @ManyToOne
    @JoinColumn(name = "farm_id")
    @Schema(description = "The farm to which the egg belongs")
    private Farm farm;

    public Egg(Integer ageInDays, Double price, Integer daysToHatch, String hatchedOrBought) {
        this.ageInDays = ageInDays;
        this.price = price;
        this.daysToHatch = daysToHatch;
        this.hatchedOrBought = hatchedOrBought;
    }
}