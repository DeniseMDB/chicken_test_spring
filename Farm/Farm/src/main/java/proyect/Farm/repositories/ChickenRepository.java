package proyect.Farm.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import proyect.Farm.entities.Chicken;

import java.util.List;

@Repository
@Transactional
public interface ChickenRepository extends CrudRepository<Chicken, Long> {
    @Query("SELECT c FROM Chicken c WHERE c.farm.id = :farmId AND c.isAlive = :isAlive")
    List<Chicken> findChickensByStatus(@Param("farmId") Long farmId, @Param("isAlive") Boolean isAlive);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "DELETE FROM chickens WHERE id = ?", nativeQuery = true)
    public void deleteById(long id);
}
