package sql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sql.models.ResultProcessEntity;

public interface ResultProcessRepository extends JpaRepository<ResultProcessEntity, Long> {
}
