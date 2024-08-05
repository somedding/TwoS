package test.test_Internet.friends;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.test_Internet.entity.UserEntity;
import test.test_Internet.repository.UserRepository;

import java.util.regex.Pattern;

@Service
public class FriendManagementService {

    // 이메일 정규표현식 패턴
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    @Autowired
    private final FriendManagementRepository friendManagementRepository;

    @Autowired
    private final UserRepository userRepository;

    private final HttpSession httpSession;

    @Autowired
    public FriendManagementService(FriendManagementRepository friendManagementRepository, UserRepository userRepository, HttpSession httpSession) {
        this.friendManagementRepository = friendManagementRepository;
        this.userRepository = userRepository;
        this.httpSession = httpSession;
    }

    public void addFriend(String friendEmail) {

        String temp = friendEmail.replaceAll("\"", "");

        String email = (String) httpSession.getAttribute("userEmail");
        UserEntity tempEntity = userRepository.findByemail(temp);

        if (!Pattern.matches(EMAIL_REGEX, temp)) {
            // 친구추가 거부 반응 만들기 1
            System.out.println("올바른 이메일 형식이 아닙니다. : " + temp);
            return;
        } else if (email.contentEquals(temp)) {
            // 친구추가 거부 반응 만들기 2
            System.out.println("본인의 이메일 입니다. : " + temp);
            return;
        } else if (tempEntity == null) {
            // 친구추가 거부 반응 만들기 3
            System.out.println("존재하지 않는 유저입니다. : " + temp);
            return;
        }

        FriendManagementEntity entity = friendManagementRepository.findByUserEmail(email);

        if (entity == null) {
            entity = new FriendManagementEntity();
            entity.setUserEmail(email);
            entity.setFriendsList(temp);
        } else {
            String existingFriends = entity.getFriendsList();
            if (existingFriends == null || existingFriends.isEmpty()) {
                entity.setFriendsList(temp);
            } else {
                // 친구 목록 이메일 콤마(",") 구분
                entity.setFriendsList(existingFriends + "," + temp);
            }
        }

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
