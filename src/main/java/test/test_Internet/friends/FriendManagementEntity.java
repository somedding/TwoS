package test.test_Internet.friends;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class FriendManagementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;
    // 친구 목록: 이메일로 저장됨 ( "," 으로 이메일 구분 )
    private String friendsList;

}
