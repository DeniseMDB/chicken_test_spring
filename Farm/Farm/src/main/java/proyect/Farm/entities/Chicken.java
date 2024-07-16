package proyect.Farm.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;
import java.util.Random;

@Entity
@Table (name = "chickens")
@Data
public class Chicken{
//    private static final Random random = new Random();
//
//    private static final int MIN_DAYS_TO_LIVE = 5;
//    private static final int MAX_DAYS_TO_LIVE_EGGS = 560;
//    private static final int MAX_DAYS_TO_LIVE_MEAT = 40;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer ageInDays;
    private Integer daysToLive;
    private Integer daysUntilNextEgg;
    private String typeOfChicken;
    private Boolean isAlive;


    @ManyToOne
    @JoinColumn(name = "farm_id")
    private Farm farm;


//    // Método privado para inicializar los días de vida según el tipo de gallina
//    private void initializeDaysToLive() {
//        if (Objects.equals(this.typeOfChicken, "eggs")) {
//            this.daysToLive = MIN_DAYS_TO_LIVE + random.nextInt(MAX_DAYS_TO_LIVE_EGGS - MIN_DAYS_TO_LIVE + 1);
//        } else {
//            this.daysToLive = MIN_DAYS_TO_LIVE + random.nextInt(MAX_DAYS_TO_LIVE_MEAT - MIN_DAYS_TO_LIVE + 1);
//        }
//    }

}
