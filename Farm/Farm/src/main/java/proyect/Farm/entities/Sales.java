package proyect.Farm.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sales")
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"farm"})
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemType;
    private Integer quantity;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "farm_id")
    private Farm farm;

    public Sales(String itemType, Integer quantity, Double price) {
        this.itemType = itemType;
        this.quantity = quantity;
        this.price = price;
    }

}