package test.test_Internet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.test_Internet.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    UserEntity findByemail(String email);
}