package test.test_Internet.UsageData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.Map;
@RestController
public class UsageDataController {
    private final UsageDataService usageDataService;

    @Autowired
    public UsageDataController(UsageDataService usageDataService) {
        this.usageDataService = usageDataService;
    }

    @PostMapping("/api/usage/upload")
    public ResponseEntity<?> uploadUsageData(@RequestBody UsageDataDTO usageDataDTO) {
        usageDataService.saveUsageData(usageDataDTO);
        double averageUsageTime = usageDataService.calculateAverageUsageTime(usageDataDTO.getEmail());
        return ResponseEntity.ok("데이터가 성공적으로 저장되었습니다. 평균 사용 시간: " + averageUsageTime + "초");
    }

    @PostMapping("/api/usage/summary")
    public ResponseEntity<?> getUsageSummary(@RequestBody String userId) {
        double totalUsageTime = usageDataService.calculateTotalUsageTime(userId);
        Map<String, Double> usageByDomain = usageDataService.calculateUsageByDomain(userId);
        return ResponseEntity.ok("총 사용 시간: " + totalUsageTime + "초, 도메인별 사용 시간: " + usageByDomain);
    }
}