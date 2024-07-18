package proyect.Farm.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import proyect.Farm.entities.Sales;

import java.util.List;

@Repository
public interface SalesRepository extends CrudRepository<Sales, Long> {
    List<Sales> findByFarm_Id(Long farmId);
}