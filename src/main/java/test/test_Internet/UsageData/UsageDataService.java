package test.test_Internet.UsageData;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import test.test_Internet.Calculate.UsageStatisticsService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UsageDataService {
    private final UsageDataRepository usageDataRepository;
    private final HttpSession httpSession;
    private final UsageStatisticsService usageStatisticsService; // 추가: UsageStatisticsService 주입

    @Autowired
    public UsageDataService(UsageDataRepository usageDataRepository, HttpSession httpSession, UsageStatisticsService usageStatisticsService) {
        this.usageDataRepository = usageDataRepository;
        this.httpSession = httpSession;
        this.usageStatisticsService = usageStatisticsService; // 주입
    }

    @Transactional
    public void saveUsageData(UsageDataDTO usageDataDTO) {
        String email = (String) httpSession.getAttribute("userEmail");

        if (email == null) {
            System.out.println("Error: No email found in session");
            return;
        }

        Map<String, Double> usageData = usageDataDTO.getUsageData();

        for (Map.Entry<String, Double> entry : usageData.entrySet()) {
            String domain = entry.getKey();
            double duration = entry.getValue();

            UsageDataEntity existingEntity = usageDataRepository.findByEmailAndDomain(email, domain);

            if (existingEntity != null) {
                existingEntity.setDuration(duration);
                existingEntity.setUpdatedAt(LocalDateTime.now());
                usageDataRepository.save(existingEntity);
            } else {
                usageDataRepository.save(new UsageDataEntity(email, domain, duration));
            }
        }

        usageStatisticsService.updateUsageStatistics(email);
    }

    public double calculateTotalUsageTime(String email) {
        List<UsageDataEntity> usageData = usageDataRepository.findByEmail(email);
        double totalUsageTime = 0;

        for (UsageDataEntity data : usageData) {
            totalUsageTime += data.getDuration();
        }

        return totalUsageTime; // 총 사용 시간 반환
    }

    public Map<String, Double> calculateUsageByDomain(String email) {
        List<UsageDataEntity> usageData = usageDataRepository.findByEmail(email);
        Map<String, Double> usageByDomain = new HashMap<>();

        for (UsageDataEntity data : usageData) {
            usageByDomain.merge(data.getDomain(), data.getDuration(), Double::sum);
        }

        return usageByDomain; // 도메인별 사용 시간 반환
    }

    public double calculateAverageUsageTime(String email) {
        List<UsageDataEntity> usageData = usageDataRepository.findByEmail(email);
        double totalUsageTime = 0;
        int count = usageData.size();

        for (UsageDataEntity data : usageData) {
            totalUsageTime += data.getDuration();
        }

        return count > 0 ? totalUsageTime / count : 0; // 평균 사용 시간 반환
    }


    public Map<String, Double> calculateDailyAverageUsageTime(String email) {
        List<UsageDataEntity> usageData = usageDataRepository.findByEmail(email);
        Map<String, List<Double>> usageByDate = new HashMap<>();

        for (UsageDataEntity data : usageData) {
            LocalDate date = data.getUpdatedAt().toLocalDate(); // LocalDateTime에서 LocalDate로 변환
            String dateString = date.toString(); // yyyy-MM-dd 형식으로 변환
            usageByDate.computeIfAbsent(dateString, k -> new ArrayList<>()).add(data.getDuration());
        }

        Map<String, Double> dailyAverage = new HashMap<>();
        for (Map.Entry<String, List<Double>> entry : usageByDate.entrySet()) {
            double average = entry.getValue().stream().mapToDouble(Double::doubleValue).average().orElse(0);
            dailyAverage.put(entry.getKey(), average);
        }

        return dailyAverage;
    }


    public Map<String, Double> calculateMonthlyAverageUsageTime(String email) {
        List<UsageDataEntity> usageData = usageDataRepository.findByEmail(email);
        Map<String, List<Double>> usageByMonth = new HashMap<>();

        for (UsageDataEntity data : usageData) {
            LocalDate date = data.getUpdatedAt().toLocalDate(); // LocalDateTime에서 LocalDate로 변환
            String monthString = date.getYear() + "-" + date.getMonthValue(); // yyyy-MM 형식으로 변환
            usageByMonth.computeIfAbsent(monthString, k -> new ArrayList<>()).add(data.getDuration());
        }

        Map<String, Double> monthlyAverage = new HashMap<>();
        for (Map.Entry<String, List<Double>> entry : usageByMonth.entrySet()) {
            double average = entry.getValue().stream().mapToDouble(Double::doubleValue).average().orElse(0);
            monthlyAverage.put(entry.getKey(), average);
        }

        return monthlyAverage;
    }
}
