package test.test_Internet.Sometimes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.test_Internet.Calculate.UsageStatisticsDTO;
import test.test_Internet.Calculate.UsageStatisticsService;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
public class SomeTimesController {

    @Autowired
    private UsageStatisticsService usageStatisticsService;

    @PostMapping("/calculate")
    public ResponseEntity<String> calculateStatistics(@RequestBody SometimesDTO dto) {
        // 이메일로 통계 업데이트
        usageStatisticsService.updateUsageStatistics(dto.getEmail());
        return ResponseEntity.ok("Statistics saved successfully.");
    }
}

