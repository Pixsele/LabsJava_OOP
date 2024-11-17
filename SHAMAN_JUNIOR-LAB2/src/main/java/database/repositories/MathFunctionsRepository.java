package database.repositories;

import org.springframework.stereotype.Repository;
import database.models.MathFunctionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface MathFunctionsRepository extends JpaRepository<MathFunctionsEntity,Long> {
    List<MathFunctionsEntity> findByName(String name);
}
