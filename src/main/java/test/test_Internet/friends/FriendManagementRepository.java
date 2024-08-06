package test.test_Internet.friends;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendManagementRepository extends JpaRepository<FriendManagementEntity, Long> {

    FriendManagementEntity findByUserEmail(String userEmail);

}
