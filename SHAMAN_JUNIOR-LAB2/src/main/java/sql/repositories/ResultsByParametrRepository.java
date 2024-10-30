package sql.repositories;

import org.springframework.stereotype.Repository;
import sql.models.ResultsByParametrEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
@Repository

public interface ResultsByParametrRepository extends JpaRepository<ResultsByParametrEntity,Long> {
}
