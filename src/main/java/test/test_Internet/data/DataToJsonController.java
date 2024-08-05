package test.test_Internet.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/data-to-json")
public class DataToJsonController {

    private final String path = "src/main/resources/static/data/";

    @Autowired
    private DataToJsonService dataToJsonService;

    @PostMapping("/users")
    public void exportUsers() throws IOException {
        try {
            dataToJsonService.exportUsersToJsonFile(path + "users.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/userInfo")
    public void exportUserInfo() throws IOException {
        try {
            dataToJsonService.exportUserInfoToJsonFile(path + "userInfo.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/usageData")
    public void exportUsageData() throws IOException {
        try {
            dataToJsonService.exportUsageDataToJson(path + "usageData.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/usageStatistics")
    public void exportUsageStatistics() throws IOException {
        try {
            dataToJsonService.exportUsageStatisticsToJson(path + "usageStatistics.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/dailyUsageStatistics")
    public void exportDailyUsageStatistics() throws IOException {
        try {
            dataToJsonService.exportDailyAverageUsageToJson(path + "dailyUsageStatistics.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/monthlyUsageStatistics")
    public void exportMonthlyUsageStatistics() throws IOException {
        try {
            dataToJsonService.exportMonthlyAverageUsageToJson(path + "monthlyUsageStatistics.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/someTime")
    public void exportSomeTime() throws IOException {
        try {
            dataToJsonService.exportSomeTimeToJson(path + "someTime.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/screenTime")
    public void exportScreenTime() throws IOException {
        try {
            dataToJsonService.exportScreenTimeToJson(path + "screenTime.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
