package test.test_Internet.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import test.test_Internet.login.UserRole;


@Getter
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Column
    private String provider; //공급자 (google, facebook ...)

    @Getter
    @Column
    private String providerId; //공급 아이디

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;

    @Builder
    public UserEntity(String name, String email, String picture, UserRole userRole, String provider, String providerId) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.userRole = userRole;
        this.provider = provider;
        this.providerId = providerId;
    }

    public UserEntity() {

    }

    public UserEntity update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey() {
        return this.userRole.getKey();
    }
}