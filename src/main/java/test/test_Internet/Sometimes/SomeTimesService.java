package test.test_Internet.Sometimes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SomeTimesService {

    @Autowired
    private SomeTimesRepository someTimesRepository;

    public void saveStatistics(double averageUsageTime, String email, LocalDateTime createdAt) {
        // 같은 이메일과 통계 유형으로 기존 데이터 찾기
        SomeTimesEntity existingStats = someTimesRepository.findByEmailAndStatType(email, "UsageStatistics");

        if (existingStats != null) {
            // 기존 데이터가 있으면 업데이트
            existingStats.setValue(averageUsageTime);
            existingStats.setCreatedAt(createdAt);
            someTimesRepository.save(existingStats);
        } else {
            // 기존 데이터가 없으면 새로 저장
            SomeTimesEntity newStats = new SomeTimesEntity();
            newStats.setEmail(email);
            newStats.setStatType("UsageStatistics"); // 하드코딩된 통계 유형
            newStats.setValue(averageUsageTime);
            newStats.setCreatedAt(createdAt);
            someTimesRepository.save(newStats);
        }
    }

    public SomeTimesEntity getStatisticsByEmail(String email) {
        return someTimesRepository.findByEmailAndStatType(email, "UsageStatistics");
    }

    public List<SomeTimesEntity> getSomeTimesByEmail(String email) {
        return someTimesRepository.findByEmail(email);
    }
}
