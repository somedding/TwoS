package test.test_Internet.friends;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendManagementService {

    private final FriendManagementRepository friendManagementRepository;
    private final HttpSession httpSession;

    @Autowired
    public FriendManagementService(FriendManagementRepository friendManagementRepository, HttpSession httpSession) {
        this.friendManagementRepository = friendManagementRepository;
        this.httpSession = httpSession;
    }

    public void addFriend(String friendEmail) {
        String email = (String) httpSession.getAttribute("userEmail");
        FriendManagementEntity entity = friendManagementRepository.findByUserEmail(email);

        if (entity == null) {
            entity = new FriendManagementEntity();
            entity.setUserEmail(email);
            entity.setFriendsList(friendEmail);
        } else {
            String existingFriends = entity.getFriendsList();
            if (existingFriends == null || existingFriends.isEmpty()) {
                entity.setFriendsList(friendEmail);
            } else {
                // 친구 목록 이메일 콤마(",") 구분
                entity.setFriendsList(existingFriends + "," + friendEmail);
            }
        }

        String temp = entity.getFriendsList().replaceAll("\"", "");
        entity.setFriendsList(temp);

        friendManagementRepository.save(entity);
    }

    public void removeFriend(String friendEmail) {
        String email = (String) httpSession.getAttribute("userEmail");
        FriendManagementEntity entity = friendManagementRepository.findByUserEmail(email);

        String friendsList = entity.getFriendsList().replace(friendEmail, "").replace(",,", ",");

        if (friendsList.startsWith(",")) {
            friendsList = friendsList.substring(1);
        }

        if (friendsList.endsWith(",")) {
            friendsList = friendsList.substring(0, friendsList.length() - 1);
        }

        entity.setFriendsList(friendsList);

        friendManagementRepository.save(entity);
    }
}
