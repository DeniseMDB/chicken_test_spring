package proyect.Farm.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import proyect.Farm.entities.Farm;

@Repository
public interface FarmRepository extends CrudRepository<Farm, Long> {
}
