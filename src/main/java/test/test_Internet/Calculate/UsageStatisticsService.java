package test.test_Internet.Calculate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.test_Internet.UsageData.UsageDataEntity;
import test.test_Internet.UsageData.UsageDataRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;

@Service
public class UsageStatisticsService {
    @Autowired
    private UsageDataRepository usageDataRepository;

    @Autowired
    private UsageStatisticsRepository usageStatisticsRepository;

    public void updateUsageStatistics(String email) {
        double totalUsageTime = calculateTotalUsageTime(email);
        Map<LocalDate, Double> personalDailyUsage = getPersonalDailyUsage(email);
        Map<YearMonth, Double> personalMonthlyUsage = getPersonalMonthlyUsage(email);

        // 기존 통계 레코드 조회
        UsageStatisticsEntity existingEntity = usageStatisticsRepository.findByEmail(email);

        if (existingEntity != null) {
            // 기존 레코드가 존재할 경우 갱신
            existingEntity.setAverageUsageTime(totalUsageTime);
            existingEntity.setDailyAverageUsage(convertMapToJson(personalDailyUsage));
            existingEntity.setMonthlyAverageUsage(convertMapToJson(personalMonthlyUsage));
            existingEntity.setCreatedAt(LocalDateTime.now());
            usageStatisticsRepository.save(existingEntity);
        } else {
            // 기존 레코드가 없을 경우 새로 생성
            UsageStatisticsEntity newEntity = new UsageStatisticsEntity();
            newEntity.setEmail(email);
            newEntity.setAverageUsageTime(totalUsageTime);
            newEntity.setDailyAverageUsage(convertMapToJson(personalDailyUsage));
            newEntity.setMonthlyAverageUsage(convertMapToJson(personalMonthlyUsage));
            newEntity.setCreatedAt(LocalDateTime.now());

            usageStatisticsRepository.save(newEntity);
        }
    }

    public UsageStatisticsDTO calculateStatistics() {
        UsageStatisticsDTO stats = new UsageStatisticsDTO();

        // 1. 평균 사용 시간
        List<Object[]> totalUsage = usageDataRepository.findTotalUsageByEmail();
        double totalDuration = totalUsage.stream()
                .mapToDouble(o -> (Double) o[1])
                .sum();
        stats.setAverageUsageTime(totalDuration / totalUsage.size());

        // 2. 일별 평균 사용 시간
        Map<LocalDate, Double> dailyAverage = new HashMap<>();
        List<Object[]> dailyUsage = usageDataRepository.findDailyUsageByEmail();
        for (Object[] row : dailyUsage) {
            java.sql.Date sqlDate = (java.sql.Date) row[1];
            LocalDate date = sqlDate.toLocalDate(); // 변환
            double duration = (Double) row[2];
            dailyAverage.merge(date, duration, Double::sum);
        }
        stats.setDailyAverageUsage(dailyAverage);

        // 3. 월별 평균 사용 시간
        Map<YearMonth, Double> monthlyAverage = new HashMap<>();
        List<Object[]> monthlyUsage = usageDataRepository.findMonthlyUsageByEmail();
        for (Object[] row : monthlyUsage) {
            int year = ((Number) row[1]).intValue(); // Integer로 변환
            int month = ((Number) row[2]).intValue(); // Integer로 변환
            double duration = (Double) row[3];

            YearMonth yearMonth = YearMonth.of(year, month);
            monthlyAverage.merge(yearMonth, duration, Double::sum);
        }
        stats.setMonthlyAverageUsage(monthlyAverage);

        return stats;
    }

    public Map<LocalDate, Double> getPersonalDailyUsage(String email) {
        List<Object[]> personalDailyUsage = usageDataRepository.findDailyUsageByEmail(email);
        Map<LocalDate, Double> dailyUsageMap = new HashMap<>();

        for (Object[] row : personalDailyUsage) {
            java.sql.Date sqlDate = (java.sql.Date) row[1];
            LocalDate date = sqlDate.toLocalDate(); // 변환
            double duration = (Double) row[2];
            dailyUsageMap.merge(date, duration, Double::sum);
        }

        return dailyUsageMap;
    }

    public Map<YearMonth, Double> getPersonalMonthlyUsage(String email) {
        List<Object[]> personalMonthlyUsage = usageDataRepository.findMonthlyUsageByEmail(email);
        Map<YearMonth, Double> monthlyUsageMap = new HashMap<>();

        for (Object[] row : personalMonthlyUsage) {
            int year = ((Number) row[1]).intValue(); // Integer로 변환
            int month = ((Number) row[2]).intValue(); // Integer로 변환
            double duration = (Double) row[3];

            YearMonth yearMonth = YearMonth.of(year, month);
            monthlyUsageMap.merge(yearMonth, duration, Double::sum);
        }

        return monthlyUsageMap;
    }

    public Map<String, Double> getPersonalDomainUsage(String email) {
        List<Object[]> domainUsage = usageDataRepository.findDomainUsageByEmail(email);
        Map<String, Double> domainMap = new HashMap<>();

        for (Object[] row : domainUsage) {
            domainMap.put((String) row[0], (Double) row[1]);
        }

        return domainMap;
    }

    private double calculateTotalUsageTime(String email) {
        List<UsageDataEntity> usageData = usageDataRepository.findByEmail(email);
        return usageData.stream().mapToDouble(UsageDataEntity::getDuration).sum();
    }

    private String convertMapToJson(Map<?, ?> map) {
        try {
            return new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }
}
