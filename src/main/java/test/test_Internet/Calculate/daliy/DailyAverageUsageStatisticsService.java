package test.test_Internet.Calculate.daliy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.test_Internet.Calculate.UsageStatisticsRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DailyAverageUsageStatisticsService {

    @Autowired
    private DailyAverageUsageStatisticsRepository dailyAverageUsageStatisticsRepository;

    @Autowired
    private UsageStatisticsRepository usageStatisticsRepository;

    public void calculateAndSaveDailyAverageUsage() {
        List<Object[]> dailyUsageList = usageStatisticsRepository.findDailyStatistics();

        Map<LocalDate, Double> dailyAverageUsage = dailyUsageList.stream()
                .collect(Collectors.toMap(
                        row -> ((java.sql.Date) row[0]).toLocalDate(),
                        row -> (Double) row[1]
                ));

        for (Map.Entry<LocalDate, Double> entry : dailyAverageUsage.entrySet()) {
            LocalDate date = entry.getKey();
            Double averageUsageTime = entry.getValue();

            DailyAverageUsageStatisticsEntity existingEntity = dailyAverageUsageStatisticsRepository.findByDate(date);
            if (existingEntity != null) {
                existingEntity.setAverageUsageTime(averageUsageTime);
                dailyAverageUsageStatisticsRepository.save(existingEntity);
            } else {
                DailyAverageUsageStatisticsEntity newEntity = new DailyAverageUsageStatisticsEntity();
                newEntity.setDate(date);
                newEntity.setAverageUsageTime(averageUsageTime);
                dailyAverageUsageStatisticsRepository.save(newEntity);
            }
        }
    }

}
