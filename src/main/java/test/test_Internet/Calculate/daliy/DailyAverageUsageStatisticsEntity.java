package test.test_Internet.Calculate.daliy;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class DailyAverageUsageStatisticsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @Column(name = "average_usage_time", columnDefinition = "DOUBLE")
    private Double averageUsageTime;
}
