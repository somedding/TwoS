package test.test_Internet.Calculate;

import org.hibernate.stat.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsageStatisticsRepository extends JpaRepository<UsageStatisticsEntity, Long> {
    UsageStatisticsEntity findByEmail(String email);

    @Query("SELECT CAST(s.createdAt AS DATE), AVG(s.averageUsageTime) FROM UsageStatisticsEntity s GROUP BY CAST(s.createdAt AS DATE)")
    List<Object[]> findDailyStatistics();

    @Query("SELECT YEAR(s.createdAt), MONTH(s.createdAt), AVG(s.averageUsageTime) FROM UsageStatisticsEntity s GROUP BY YEAR(s.createdAt), MONTH(s.createdAt)")
    List<Object[]> findMonthlyStatistics();
}
