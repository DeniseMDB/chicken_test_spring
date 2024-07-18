package proyect.Farm.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import proyect.Farm.entities.FarmReport;

@Repository
public interface FarmReportRepository extends CrudRepository<FarmReport , Long> {
}
