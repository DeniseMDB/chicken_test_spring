package proyect.Farm.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "eggs")
@Data
public class Egg {
//    private static final Integer DAYS_TO_HATCH = 21;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date createdAt;
    private Double price;
    private Integer ageInDays;
    private Integer daysToHatch;

    @ManyToOne
    @JoinColumn(name = "farm_id")
    private Farm farm;


}
