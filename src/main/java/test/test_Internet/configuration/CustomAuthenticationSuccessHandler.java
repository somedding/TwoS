package test.test_Internet.configuration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import test.test_Internet.data.DataToJsonService;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private DataToJsonService dataToJsonService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String basePath = "src/main/resources/static/data/";

        // 각종 데이터 JSON으로 변환
        dataToJsonService.exportAllDataToJson(basePath + "all.json");
        /*
        dataToJsonService.exportUserInfoToJsonFile(basePath + "userInfo.json");
        dataToJsonService.exportScreenTimeToJson(basePath + "screenTime.json");
        dataToJsonService.exportSomeTimeToJson(basePath + "someTime.json");
        dataToJsonService.exportUsageDataToJson(basePath + "usageData.json");
        dataToJsonService.exportUsageStatisticsToJson(basePath + "usageStatistics.json");
        dataToJsonService.exportDailyAverageUsageToJson(basePath + "dailyAverageUsage.json");
        dataToJsonService.exportMonthlyAverageUsageToJson(basePath + "monthlyAverageUsage.json");
        dataToJsonService.exportUsersToJsonFile(basePath + "users.json");
        */
        // 로그인 성공 후 리다이렉트할 URL
        response.sendRedirect("/sweetodo/todo/todoMain");
    }
}

