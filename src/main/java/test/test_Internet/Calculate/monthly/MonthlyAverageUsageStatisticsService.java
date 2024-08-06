package test.test_Internet.Calculate.monthly;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.test_Internet.Calculate.UsageStatisticsRepository;

import java.time.Month;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MonthlyAverageUsageStatisticsService {

    @Autowired
    private MonthlyAverageUsageStatisticsRepository monthlyAverageUsageStatisticsRepository;

    @Autowired
    private UsageStatisticsRepository usageStatisticsRepository;

    @Transactional
    public void calculateAndSaveMonthlyAverageUsage() {
        List<Object[]> monthlyUsageList = usageStatisticsRepository.findMonthlyStatistics();

        Map<YearMonth, Double> monthlyAverageUsage = monthlyUsageList.stream()
                .collect(Collectors.toMap(
                        row -> YearMonth.of(((Number) row[0]).intValue(), ((Number) row[1]).intValue()),
                        row -> (Double) row[2]
                ));

        for (Map.Entry<YearMonth, Double> entry : monthlyAverageUsage.entrySet()) {
            YearMonth yearMonth = entry.getKey();
            Double averageUsageTime = entry.getValue();

            MonthlyAverageUsageStatisticsEntity existingEntity = monthlyAverageUsageStatisticsRepository.findByYearMonth(yearMonth);
            if (existingEntity != null) {
                existingEntity.setAverageUsageTime(averageUsageTime);
                monthlyAverageUsageStatisticsRepository.save(existingEntity);
            } else {
                MonthlyAverageUsageStatisticsEntity newEntity = new MonthlyAverageUsageStatisticsEntity();
                newEntity.setYearMonth(yearMonth);
                newEntity.setAverageUsageTime(averageUsageTime);
                monthlyAverageUsageStatisticsRepository.save(newEntity);
            }
        }
    }

}
