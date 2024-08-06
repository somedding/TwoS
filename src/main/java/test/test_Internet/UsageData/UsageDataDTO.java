package test.test_Internet.UsageData;

import test.test_Internet.entity.UserEntity;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UsageDataDTO implements Serializable {
    private String email;
    private Map<String, Double> usageData; // 도메인 별 사용 시간
    private String updatedAt;

    // Getter 및 Setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, Double> getUsageData() {
        return usageData;
    }

    public void setUsageData(Map<String, Double> usageData) {
        this.usageData = usageData;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    // OAuth 사용자 정보를 처리하는 메서드
    public void handleOAuthUser(UserEntity userEntity, UsageDataService usageDataService) {
        String email = userEntity.getEmail();

        this.setEmail(email); // userId 설정

        // 예시로 usageData를 초기화
        this.setUsageData(new HashMap<>()); // 사용 데이터 초기화

        // 이후에 usageDataDTO를 사용하여 데이터 저장
        usageDataService.saveUsageData(this);
    }

}
