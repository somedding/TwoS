package test.test_Internet.UsageData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UsageDataRepository extends JpaRepository<UsageDataEntity, Long> {

    List<UsageDataEntity> findByEmail(String email); // 사용자 ID로 사용 데이터 조회
//    UsageDataEntity findByEmailAndDomain(String email, String domain); // email과 domain으로 사용 데이터 조회
    UsageDataEntity findByEmailAndDomainAndCreatedAt(String domain, String email, LocalDate createdAt); // email, domain, createdAt으로 사용 데이터 조회

    @Query("SELECT u.email, SUM(u.duration) FROM UsageDataEntity u GROUP BY u.email")
    List<Object[]> findTotalUsageByEmail();

    @Query("SELECT u.email, CAST(u.updatedAt AS DATE), SUM(u.duration) FROM UsageDataEntity u GROUP BY u.email, CAST(u.updatedAt AS DATE)")
    List<Object[]> findDailyUsageByEmail();

    @Query("SELECT u.email, YEAR(u.updatedAt), MONTH(u.updatedAt), SUM(u.duration) FROM UsageDataEntity u GROUP BY u.email, YEAR(u.updatedAt), MONTH(u.updatedAt)")
    List<Object[]> findMonthlyUsageByEmail();

    @Query("SELECT u.domain, SUM(u.duration) FROM UsageDataEntity u WHERE u.email = :email GROUP BY u.domain")
    List<Object[]> findDomainUsageByEmail(@Param("email") String email);

    @Query("SELECT u.email, CAST(u.updatedAt AS DATE), SUM(u.duration) FROM UsageDataEntity u WHERE u.email = :email GROUP BY u.email, CAST(u.updatedAt AS DATE)")
    List<Object[]> findDailyUsageByEmail(@Param("email") String email);

    @Query("SELECT u.email, YEAR(u.updatedAt), MONTH(u.updatedAt), SUM(u.duration) FROM UsageDataEntity u WHERE u.email = :email GROUP BY u.email, YEAR(u.updatedAt), MONTH(u.updatedAt)")
    List<Object[]> findMonthlyUsageByEmail(@Param("email") String email);
}