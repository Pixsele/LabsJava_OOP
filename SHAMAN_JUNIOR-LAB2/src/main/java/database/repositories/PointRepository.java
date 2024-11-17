package database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import database.models.MathFunctionsEntity;
import database.models.PointEntity;

import java.util.List;

public interface PointRepository extends JpaRepository<PointEntity, Long> {
    List<PointEntity> findByFunction(MathFunctionsEntity function);
}
