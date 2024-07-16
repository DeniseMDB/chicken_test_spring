package proyect.Farm.entities;

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

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Chicken> chickens;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Egg> eggs;
}
