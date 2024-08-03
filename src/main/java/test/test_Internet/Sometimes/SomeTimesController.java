package test.test_Internet.Sometimes;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import test.test_Internet.Calculate.UsageStatisticsDTO;
import test.test_Internet.Calculate.UsageStatisticsService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
public class SomeTimesController {

    @Autowired
    private SomeTimesService someTimesService;

    @Autowired
    private UsageStatisticsService usageStatisticsService;


    @Autowired
    private UsageStatisticsService statisticsService;

    @PostMapping("/calculate")
    public ResponseEntity<String> calculateStatistics(@RequestBody SometimesDTO dto) {
        // 이메일로 통계 업데이트
        usageStatisticsService.updateUsageStatistics(dto.getEmail());
        return ResponseEntity.ok("Statistics saved successfully.");
    }

    @GetMapping("/some-times")
    public ResponseEntity<List<SomeTimesEntity>> getSomeTimes(@RequestParam String email) {
        List<SomeTimesEntity> someTimesList = someTimesService.getSomeTimesByEmail(email);
        if (someTimesList.isEmpty()) {
            return ResponseEntity.notFound().build(); // 데이터가 없을 경우 404 응답
        }
        return ResponseEntity.ok(someTimesList);
    }
}