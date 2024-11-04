package sql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sql.models.ProcessEntity;

public interface ProcessRepository extends JpaRepository<ProcessEntity, Long> {
}
