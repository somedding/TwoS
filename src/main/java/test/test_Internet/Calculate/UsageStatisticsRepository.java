package test.test_Internet.Calculate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsageStatisticsRepository extends JpaRepository<UsageStatisticsEntity, Long> {
    UsageStatisticsEntity findByEmail(String email);
}
