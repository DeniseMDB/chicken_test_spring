package proyect.Farm.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "farm")
@Data
@Schema(description = "Represents a farm that manages chickens and eggs")
public class Farm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the farm", example = "1")
    private Long id;

    @Schema(description = "Amount of money the farm has", example = "1000.0")
    private Double money;

    @Schema(description = "Maximum number of eggs the farm can have", example = "100")
    private Integer maxEggs;

    @Schema(description = "Maximum number of chickens the farm can have", example = "50")
    private Integer maxChickens;

    @Schema(description = "Number of days the farm has been in business", example = "0")
    private Integer daysInBusiness;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Schema(description = "List of chickens in the farm")
    private List<Chicken> chickens;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Schema(description = "List of eggs in the farm")
    private List<Egg> eggs;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Schema(description = "List of sales made by the farm")
    private List<Sales> sales;

    public Farm() {
    }
}
