package test.test_Internet.UsageData;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UsageDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String domain;
    private double duration; // 사용 시간

    // getUpdatedAt() 메서드 추가
    @Getter
    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // 업데이트 시간 필드 추가

    // 매개변수가 있는 생성자
    public UsageDataEntity(String email, String domain, double duration, LocalDateTime updatedAt) {
        this.email = email;
        this.domain = domain;
        this.duration = duration;
        this.updatedAt = updatedAt; // 업데이트 시간 초기화
    }

    // 새로운 생성자 추가 (updatedAt을 기본값으로 설정)
    public UsageDataEntity(String userId, String domain, double duration) {
        this(userId, domain, duration, LocalDateTime.now()); // 현재 시간으로 초기화
    }
}
