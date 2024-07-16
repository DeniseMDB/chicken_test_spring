package proyect.Farm.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import proyect.Farm.entities.Chicken;

import java.util.List;

@Repository
public interface ChickenRepository extends CrudRepository<Chicken, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM chickens WHERE isAlive = ?")
    public List<Chicken> findChickensAlive(Boolean isAlive);
}
