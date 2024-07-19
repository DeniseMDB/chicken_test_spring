package proyect.Farm.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "farm")
@Data
public class Farm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double money;
    private Integer maxEggs;
    private Integer maxChickens;
    private Integer daysInBusiness;//deberia ser igual a 0 cuando se crea y simboliza los dias que pasan

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Chicken> chickens;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Egg> eggs;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Sales> sales;

    public Farm() {
    }
}
