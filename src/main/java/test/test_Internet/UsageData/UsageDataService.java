package test.test_Internet.UsageData;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import test.test_Internet.Calculate.UsageStatisticsService;
import test.test_Internet.ScreenTime.ScreenTimeDTO;
import test.test_Internet.ScreenTime.ScreenTimeEntity;
import test.test_Internet.ScreenTime.ScreenTimeService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UsageDataService {
    private final UsageDataRepository usageDataRepository;
    private final HttpSession httpSession;
    private final UsageStatisticsService usageStatisticsService;
    private final ScreenTimeService screenTimeService;

    @Autowired
    public UsageDataService(UsageDataRepository usageDataRepository,
                            HttpSession httpSession,
                            UsageStatisticsService usageStatisticsService,
                            ScreenTimeService screenTimeService) {
        this.usageDataRepository = usageDataRepository;
        this.httpSession = httpSession;
        this.usageStatisticsService = usageStatisticsService;
        this.screenTimeService = screenTimeService;
    }

    @Transactional
    public void saveUsageData(UsageDataDTO usageDataDTO) {
        String email = (String) httpSession.getAttribute("userEmail");

        if (email == null) {
            throw new IllegalStateException("No email found in session");
        }

        Map<String, Double> usageData = usageDataDTO.getUsageData();
        LocalDate today = LocalDate.now();

        // 스크린 타임 데이터를 위한 변수 초기화
        Map<String, Double> screenTimeData = new HashMap<>();

        for (Map.Entry<String, Double> entry : usageData.entrySet()) {
            String domain = entry.getKey();
            double duration = entry.getValue();

            UsageDataEntity existingEntity = usageDataRepository.findByEmailAndDomainAndCreatedAt(email, domain, today);

            // 받아온 정보 중 email, domain, today 에 해당하는 데이터가
            // 이미 존재 할 경우에는 갱신하고 존재하지 않을 경우에는 생성
            if (existingEntity != null) {
                existingEntity.setDuration(duration);
                existingEntity.setUpdatedAt(LocalDateTime.now());
                usageDataRepository.save(existingEntity);
            } else {
                UsageDataEntity newEntity = new UsageDataEntity();
                newEntity.setEmail(email);
                newEntity.setDomain(domain);
                newEntity.setDuration(duration);
                newEntity.setUpdatedAt(LocalDateTime.now());
                newEntity.setCreatedAt(today);
                usageDataRepository.save(newEntity);
            }

            // ScreenTimeService의 classifyDomain 메서드를 호출하여 도메인 카테고리 분류
            String category = screenTimeService.classifyDomain(domain); // ScreenTimeService에서 호출

            // 스크린 타임 데이터에 카테고리 추가
            screenTimeData.merge(category, duration, Double::sum);
        }

        // 스크린 타임 데이터 저장
        for (Map.Entry<String, Double> entry : screenTimeData.entrySet()) {
            ScreenTimeDTO screenTimeDTO = new ScreenTimeDTO();
            screenTimeDTO.setCategory(entry.getKey());
            screenTimeDTO.setTotalDuration(entry.getValue());
            screenTimeDTO.setPercentage((entry.getValue() / calculateTotalUsageTime(email)) * 100); // 비율 계산

            saveScreenTimeData(screenTimeDTO);
        }

        usageStatisticsService.updateUsageStatistics(email);
    }

    // 스크린 타임 데이터를 저장하는 메서드 추가
    private void saveScreenTimeData(ScreenTimeDTO screenTimeDTO) {
        String email = (String) httpSession.getAttribute("userEmail");
        LocalDateTime now = LocalDateTime.now();

        ScreenTimeEntity screenTimeEntity = new ScreenTimeEntity();
        screenTimeEntity.setCategory(screenTimeDTO.getCategory());
        screenTimeEntity.setTotalDuration(screenTimeDTO.getTotalDuration());
        screenTimeEntity.setPercentage(screenTimeDTO.getPercentage());
        screenTimeEntity.setEmail(email); // 이메일 설정
        screenTimeEntity.setUpdatedAt(now); // 현재 시간 설정

        screenTimeService.save(screenTimeEntity);
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
