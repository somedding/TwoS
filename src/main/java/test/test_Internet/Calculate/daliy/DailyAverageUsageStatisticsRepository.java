package test.test_Internet.Calculate.daliy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface DailyAverageUsageStatisticsRepository extends JpaRepository<DailyAverageUsageStatisticsEntity, Long> {
    DailyAverageUsageStatisticsEntity findByDate(LocalDate date);
}
