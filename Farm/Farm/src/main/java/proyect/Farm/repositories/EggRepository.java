package proyect.Farm.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import proyect.Farm.entities.Egg;

@Repository
public interface EggRepository extends CrudRepository<Egg, Long> {
}
