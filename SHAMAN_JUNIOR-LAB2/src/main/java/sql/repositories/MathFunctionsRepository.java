package sql.repositories;

import org.springframework.stereotype.Repository;
import sql.models.MathFunctionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface MathFunctionsRepository extends JpaRepository<MathFunctionsEntity,Long> {
}
