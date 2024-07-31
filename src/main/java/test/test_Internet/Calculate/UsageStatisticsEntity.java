package test.test_Internet.Calculate;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "USAGE_STATISTICS")
public class UsageStatisticsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double averageUsageTime;

    @Column(name = "daily_average_usage")
    private String dailyAverageUsage; // JSON 형태로 저장할 수 있음

    @Column(name = "monthly_average_usage")
    private String monthlyAverageUsage; // JSON 형태로 저장할 수 있음

    private String email;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAverageUsageTime() {
        return averageUsageTime;
    }

    public void setAverageUsageTime(Double averageUsageTime) {
        this.averageUsageTime = averageUsageTime;
    }

    public String getDailyAverageUsage() {
        return dailyAverageUsage;
    }

    public void setDailyAverageUsage(String dailyAverageUsage) {
        this.dailyAverageUsage = dailyAverageUsage;
    }

    public String getMonthlyAverageUsage() {
        return monthlyAverageUsage;
    }

    public void setMonthlyAverageUsage(String monthlyAverageUsage) {
        this.monthlyAverageUsage = monthlyAverageUsage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
