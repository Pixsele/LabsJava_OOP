package sql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sql.models.PointEntity;

public interface PointRepository extends JpaRepository<PointEntity, Long> {
}
