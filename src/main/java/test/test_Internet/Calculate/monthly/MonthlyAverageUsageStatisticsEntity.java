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
    private YearMonth yearMonth;
    private Double averageUsageTime;
}
