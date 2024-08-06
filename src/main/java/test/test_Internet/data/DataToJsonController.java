package test.test_Internet.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/data-to-json")
public class DataToJsonController {

    private final String path = "src/main/resources/static/data/";

    @Autowired
    private DataToJsonService dataToJsonService;

    @Autowired
    private HttpSession httpSession;


    @PostMapping("/export")
    public void exportAll(@RequestParam String email) throws IOException {
        // 이메일에서 '@' 기호 이전 부분을 가져와서 파일명으로 사용
        String fileName = "all_" + email;
        String jsonFilePath = path + fileName + ".json";

        try {
            dataToJsonService.exportAllDataToJson(jsonFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/all") // 새로운 엔드포인트 추가
    public void exportAllData() throws IOException {
        String email = (String) httpSession.getAttribute("userEmail");
        if (email == null) {
            throw new IllegalArgumentException("User email not found in session");
        }
        String fileName = "all_" + email;
        String jsonFilePath = path + fileName + ".json";

        try {
            dataToJsonService.exportAllDataToJson(jsonFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/*
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

    @PostMapping("/data")
    public void exportData() throws IOException {
        try {
            dataToJsonService.exportDataToJson(path + "data.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/friends")
    public void exportFriends() throws IOException {
        try {
            dataToJsonService.exportFriendsListToJson(path + "friends.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/
}
