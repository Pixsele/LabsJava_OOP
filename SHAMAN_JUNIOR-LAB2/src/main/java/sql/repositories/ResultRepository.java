package sql.repositories;

import org.springframework.stereotype.Repository;
import sql.models.ResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
@Repository

public interface ResultRepository extends JpaRepository<ResultEntity,Long> {
}
