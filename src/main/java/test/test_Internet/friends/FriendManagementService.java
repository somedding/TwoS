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

        // 내 친구 목록에 친구 정보 추가
        String email = (String) httpSession.getAttribute("userEmail");
        UserEntity tempEntity = userRepository.findByemail(temp);

        FriendManagementEntity entity = friendManagementRepository.findByUserEmail(email);

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
        } else if (entity != null && entity.getFriendsList().contains(temp)) {
            // 친구추가 거부 반응 만들기 4
            System.out.println("이미 친구추가가 된 유저입니다. : " + temp);
            return;
        }

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

        // 친구의 친구 목록에서 내 정보 추가
        FriendManagementEntity Fentity = friendManagementRepository.findByUserEmail(temp);

        if (Fentity == null) {
            Fentity = new FriendManagementEntity();
            Fentity.setUserEmail(temp);
            Fentity.setFriendsList(email);
        } else {
            String existingFriends = Fentity.getFriendsList();
            if (existingFriends == null || existingFriends.isEmpty()) {
                Fentity.setFriendsList(email);
            } else {
                // 친구 목록 이메일 콤마(",") 구분
                Fentity.setFriendsList(existingFriends + "," + email);
            }
        }

        friendManagementRepository.save(Fentity);
    }

    public void removeFriend(String friendEmail) {
        String temp = friendEmail.replaceAll("\"", "");

        // 내 친구 목록에서 친구 정보 삭제
        String email = (String) httpSession.getAttribute("userEmail");
        FriendManagementEntity entity = friendManagementRepository.findByUserEmail(email);

        String friendsList = entity.getFriendsList().replace(temp, "").replace(",,", ",");

        if (friendsList.startsWith(",")) {
            friendsList = friendsList.substring(1);
        }

        if (friendsList.endsWith(",")) {
            friendsList = friendsList.substring(0, friendsList.length() - 1);
        }

        entity.setFriendsList(friendsList);

        friendManagementRepository.save(entity);

        // 친구의 친구 목록에서 내 정보 삭제
        FriendManagementEntity entity1 = friendManagementRepository.findByUserEmail(temp);

        String friendsList1 = entity1.getFriendsList().replace(email, "").replace(",,", ",");

        if (friendsList1.startsWith(",")) {
            friendsList1 = friendsList1.substring(1);
        }

        if (friendsList1.endsWith(",")) {
            friendsList1 = friendsList1.substring(0, friendsList1.length() - 1);
        }

        entity1.setFriendsList(friendsList1);

        friendManagementRepository.save(entity1);
    }

}
