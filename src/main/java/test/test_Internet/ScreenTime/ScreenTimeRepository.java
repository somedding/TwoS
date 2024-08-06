package test.test_Internet.ScreenTime;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreenTimeRepository extends JpaRepository<ScreenTimeEntity, Long> {
    ScreenTimeEntity findByEmailAndCategory(String email, String category);
    List<ScreenTimeEntity> findByEmail(String email);
}

