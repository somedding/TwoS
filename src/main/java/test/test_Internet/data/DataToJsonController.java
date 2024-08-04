package test.test_Internet.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/data-to-json")
public class DataToJsonController {

    private final String path = "src/main/resources/static/";

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

}
