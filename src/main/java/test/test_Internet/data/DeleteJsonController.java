package test.test_Internet.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/delete-json"})
public class DeleteJsonController {

    private final String path = "src/main/resources/static/data/";

    @Autowired
    private DeleteJsonService deleteJsonService;

    @PostMapping("/userInfo")
    public void deleteUserInfo() {
        deleteJsonService.deleteFileIfExists(path + "userInfo.json");
    }

    @PostMapping("/users")
    public void deleteUsers() {
        deleteJsonService.deleteFileIfExists(path + "users.json");
    }

    @PostMapping("/screenTime")
    public void deleteScreenTime() {
        deleteJsonService.deleteFileIfExists(path + "screenTime.json");
    }

    @PostMapping("/someTime")
    public void deleteSomeTime() {
        deleteJsonService.deleteFileIfExists(path + "someTime.json");
    }

    @PostMapping("/usageStatistics")
    public void deleteUsageStatistics() {
        deleteJsonService.deleteFileIfExists(path + "usageStatistics.json");
    }

    @PostMapping("/usageData")
    public void deleteUsageData() {
        deleteJsonService.deleteFileIfExists(path + "usageData.json");
    }

    @PostMapping("/data")
    public void deleteData() {
        deleteJsonService.deleteFileIfExists(path + "data.json");
    }

}
