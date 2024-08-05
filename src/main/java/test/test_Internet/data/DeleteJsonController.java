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

}
