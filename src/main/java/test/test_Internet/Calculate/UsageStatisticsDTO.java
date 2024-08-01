package test.test_Internet.Calculate;

import java.util.Map;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;

public class UsageStatisticsDTO {
    private double averageUsageTime; // 평균 사용 시간
    private Map<LocalDate, Double> dailyAverageUsage; // 일별 평균 사용 시간
    private Map<YearMonth, Double> monthlyAverageUsage; // 월별 평균 사용 시간
    private String email; // 사용자 이메일

    // Getter 및 Setter
    public double getAverageUsageTime() {
        return averageUsageTime;
    }

    public void setAverageUsageTime(double averageUsageTime) {
        this.averageUsageTime = averageUsageTime;
    }

    public Map<LocalDate, Double> getDailyAverageUsage() {
        return dailyAverageUsage;
    }

    public void setDailyAverageUsage(Map<LocalDate, Double> dailyAverageUsage) {
        this.dailyAverageUsage = dailyAverageUsage;
    }

    public Map<YearMonth, Double> getMonthlyAverageUsage() {
        return monthlyAverageUsage;
    }

    public void setMonthlyAverageUsage(Map<YearMonth, Double> monthlyAverageUsage) {
        this.monthlyAverageUsage = monthlyAverageUsage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
