package proyect.Farm.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "farm_products")
public abstract class FarmProduct {
    private LocalDate dateCreated;
    private boolean isSold;
    private double price;

}
