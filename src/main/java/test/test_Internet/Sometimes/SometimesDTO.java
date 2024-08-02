package test.test_Internet.Sometimes;

import java.time.LocalDateTime;

public class SometimesDTO {
    private double averageUsageTime;
    private String email;
    private LocalDateTime createdAt; // 추가된 필드

    // Getters and Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public double getAverageUsageTime() { return averageUsageTime; }
    public void setAverageUsageTime(double averageUsageTime) { this.averageUsageTime = averageUsageTime; }

    public LocalDateTime getCreatedAt() { return createdAt; } // 추가된 Getter
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; } // 추가된 Setter
}
