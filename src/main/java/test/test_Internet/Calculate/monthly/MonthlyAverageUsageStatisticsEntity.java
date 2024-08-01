package test.test_Internet.Calculate.monthly;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.YearMonth;

@Getter
@Setter
@Entity
public class MonthlyAverageUsageStatisticsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = YearMonthAttributeconverter.class)
    @Column(name = "ym")
    private YearMonth yearMonth;

    @Column(name = "average_usage_time", columnDefinition = "DOUBLE")
    private Double averageUsageTime;
}
