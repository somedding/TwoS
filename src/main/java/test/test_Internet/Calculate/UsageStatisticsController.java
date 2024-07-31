package test.test_Internet.Calculate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;

@RestController
@RequestMapping("/usage-statistics")
public class UsageStatisticsController {
    @Autowired
    private UsageStatisticsService usageStatisticsService;

    @GetMapping("/average")
    public ResponseEntity<UsageStatisticsDTO> getAverageUsage() {
        UsageStatisticsDTO stats = usageStatisticsService.calculateStatistics();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/personal/daily")
    public ResponseEntity<Map<LocalDate, Double>> getPersonalDailyUsage(@RequestParam String email) {
        Map<LocalDate, Double> personalDailyUsage = usageStatisticsService.getPersonalDailyUsage(email);
        return ResponseEntity.ok(personalDailyUsage);
    }

    @GetMapping("/personal/monthly")
    public ResponseEntity<Map<YearMonth, Double>> getPersonalMonthlyUsage(@RequestParam String email) {
        Map<YearMonth, Double> personalMonthlyUsage = usageStatisticsService.getPersonalMonthlyUsage(email);
        return ResponseEntity.ok(personalMonthlyUsage);
    }

    @GetMapping("/personal/domain")
    public ResponseEntity<Map<String, Double>> getPersonalDomainUsage(@RequestParam String email) {
        Map<String, Double> personalDomainUsage = usageStatisticsService.getPersonalDomainUsage(email);
        return ResponseEntity.ok(personalDomainUsage);
    }
}
