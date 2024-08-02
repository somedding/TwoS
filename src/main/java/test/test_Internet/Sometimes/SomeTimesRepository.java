package test.test_Internet.Sometimes;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SomeTimesRepository extends JpaRepository<SomeTimesEntity, Long> {
    // 이메일과 통계 유형으로 조회하는 메서드
    SomeTimesEntity findByEmailAndStatType(String email, String statType);
}
