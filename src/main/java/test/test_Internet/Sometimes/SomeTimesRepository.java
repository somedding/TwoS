package test.test_Internet.Sometimes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SomeTimesRepository extends JpaRepository<SomeTimesEntity, Long> {
    // 이메일과 통계 유형으로 조회하는 메서드
    SomeTimesEntity findByEmailAndStatType(String email, String statType);
    List<SomeTimesEntity> findByEmail(String email);
}
