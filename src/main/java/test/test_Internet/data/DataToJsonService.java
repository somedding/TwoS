package test.test_Internet.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.test_Internet.Calculate.UsageStatisticsEntity;
import test.test_Internet.Calculate.UsageStatisticsRepository;
import test.test_Internet.Calculate.daliy.DailyAverageUsageStatisticsEntity;
import test.test_Internet.Calculate.daliy.DailyAverageUsageStatisticsRepository;
import test.test_Internet.Calculate.monthly.MonthlyAverageUsageStatisticsEntity;
import test.test_Internet.Calculate.monthly.MonthlyAverageUsageStatisticsRepository;
import test.test_Internet.Sometimes.SomeTimesEntity;
import test.test_Internet.Sometimes.SomeTimesRepository;
import test.test_Internet.UsageData.UsageDataEntity;
import test.test_Internet.UsageData.UsageDataRepository;
import test.test_Internet.entity.UserEntity;
import test.test_Internet.repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class DataToJsonService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UsageDataRepository usageDataRepository;

    @Autowired
    private UsageStatisticsRepository usageStatisticsRepository;

    @Autowired
    private DailyAverageUsageStatisticsRepository dailyAverageUsageStatisticsRepository;

    @Autowired
    private MonthlyAverageUsageStatisticsRepository monthlyAverageUsageStatisticsRepository;

    @Autowired
    private SomeTimesRepository someTimesRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HttpSession httpSession;

    public DataToJsonService(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public void exportSomeTimeToJson(String filePath) throws IOException {
        String email = (String) httpSession.getAttribute("userEmail");
        List<SomeTimesEntity> someTimes = someTimesRepository.findByEmail(email);

        System.out.println(someTimes + " / " + email);

        File file = saveJsonFile(filePath);

        objectMapper.writeValue(file, someTimes);

    }

    public void exportDailyAverageUsageToJson(String filePath) throws IOException {
        List<DailyAverageUsageStatisticsEntity> dailyAverageUsageStatistics = dailyAverageUsageStatisticsRepository.findAll();

        File file = saveJsonFile(filePath);

        objectMapper.writeValue(file, dailyAverageUsageStatistics);
    }

    public void exportMonthlyAverageUsageToJson(String filePath) throws IOException {
        List<MonthlyAverageUsageStatisticsEntity> monthlyAverageUsageStatistics = monthlyAverageUsageStatisticsRepository.findAll();

        File file = saveJsonFile(filePath);

        objectMapper.writeValue(file, monthlyAverageUsageStatistics);
    }

    //
    public void exportUsageStatisticsToJson(String filePath) throws IOException {
        List<UsageStatisticsEntity> usageStatistics = usageStatisticsRepository.findAll();

        File file = saveJsonFile(filePath);

        objectMapper.writeValue(file, usageStatistics);
    }

    // 로그인한 유저 사용시간 json 변환
    public void exportUsageDataToJson(String filePath) throws IOException {
        String email = (String) httpSession.getAttribute("userEmail");
        List<UsageDataEntity> usageData = usageDataRepository.findByEmail(email);

        File file = saveJsonFile(filePath);

        objectMapper.writeValue(file, usageData);
    }

    // 로그인한 유저 정보 json 변환
    public void exportUserInfoToJsonFile(String filePath) throws IOException {
        String email = (String) httpSession.getAttribute("uesrEmail");
        UserEntity users = userRepository.findByemail(email);

        File file = saveJsonFile(filePath);

        objectMapper.writeValue(file, users);
    }

    // 모든 유저 정보 json 변환
    public void exportUsersToJsonFile(String filePath) throws IOException {
        List<UserEntity> users = userRepository.findAll();

        File file = saveJsonFile(filePath);

        objectMapper.writeValue(file, users);
    }

    public File saveJsonFile(String filePath) throws IOException {
        // 파일 객체 생성
        File file = new File(filePath);

        // 부모 디렉토리 경로를 추출하고 디렉토리가 존재하지 않으면 생성
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                throw new IOException("Failed to create directory: " + parentDir.getAbsolutePath());
            }
        }

        // 파일이 존재하면 삭제
        if (file.exists()) {
            if (!file.delete()) {
                throw new IOException("Unable to delete existing file: " + filePath);
            }
        }

        // 새 파일 생성 및 JSON 작성
        if (!file.createNewFile()) {
            throw new IOException("Failed to create new file: " + filePath);
        }

        return file;

    }
}
