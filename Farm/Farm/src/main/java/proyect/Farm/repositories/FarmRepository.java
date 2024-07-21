package proyect.Farm.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import proyect.Farm.entities.Chicken;
import proyect.Farm.entities.Farm;

import java.util.List;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Long> {
}
