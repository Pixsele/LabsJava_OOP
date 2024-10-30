package sql.security;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Long>{
    UserEntity findByUsername(String username);
}
