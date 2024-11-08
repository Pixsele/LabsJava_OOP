package db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import db.models.MathFunctionsEntity;
import db.models.PointEntity;

import java.util.List;

public interface PointRepository extends JpaRepository<PointEntity, Long> {
    List<PointEntity> findByFunction(MathFunctionsEntity function);
}
