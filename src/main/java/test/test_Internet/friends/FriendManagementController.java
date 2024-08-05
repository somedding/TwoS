package test.test_Internet.friends;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/friends")
public class FriendManagementController {

    private final FriendManagementService friendManagementService;

    @Autowired
    public FriendManagementController(FriendManagementService friendManagementService) {
        this.friendManagementService = friendManagementService;
    }

    @PostMapping("/add")
    public void addToFriends(@RequestParam String friendEmail) {
        friendManagementService.addFriend(friendEmail);
    }

    @PostMapping("/remove")
    public void removeToFriend(@RequestParam String friendEmail) { friendManagementService.removeFriend(friendEmail); }

}
