package test.test_Internet.Calculate.monthly;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;

@Repository
public interface MonthlyAverageUsageStatisticsRepository extends JpaRepository<MonthlyAverageUsageStatisticsEntity, Long> {
    MonthlyAverageUsageStatisticsEntity findByYearMonth(YearMonth yearMonth);
}
