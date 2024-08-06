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
import test.test_Internet.ScreenTime.ScreenTimeEntity;
import test.test_Internet.ScreenTime.ScreenTimeRepository;
import test.test_Internet.Sometimes.SomeTimesEntity;
import test.test_Internet.Sometimes.SomeTimesRepository;
import test.test_Internet.UsageData.UsageDataEntity;
import test.test_Internet.UsageData.UsageDataRepository;
import test.test_Internet.entity.UserEntity;
import test.test_Internet.friends.FriendManagementEntity;
import test.test_Internet.friends.FriendManagementRepository;
import test.test_Internet.repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private ScreenTimeRepository screenTimeRepository;

    @Autowired
    private FriendManagementRepository friendManagementRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HttpSession httpSession;

    public DataToJsonService(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public void exportAllDataToJson(String filePath) throws IOException {
        String email = (String) httpSession.getAttribute("userEmail");
        Map<String, Object> allData = new HashMap<>();

        // 친구 목록 데이터
        addFriendData(email, allData);

        // 사용 데이터
        addUsageData(email, allData);

        // Screen Time 데이터
        addScreenTimeData(email, allData);

        // Some Time 데이터
        addSomeTimesData(email, allData);

        // Daily Average Usage 데이터
        addDailyAverageUsageData(allData);

        // Monthly Average Usage 데이터
        addMonthlyAverageUsageData(allData);

        // Usage Statistics 데이터
        addUsageStatisticsData(allData);

        // 유저 정보 데이터
        addUserInfo(email, allData);

        // 모든 유저 정보 데이터
        addAllUsersData(allData);

        // JSON 파일로 저장
        File file = saveJsonFile(filePath);
        objectMapper.writeValue(file, allData);
    }

    private void addFriendData(String email, Map<String, Object> allData) {
        FriendManagementEntity friends = friendManagementRepository.findByUserEmail(email);
        if (friends == null) {
            Map<String, String> map = new HashMap<>();
            map.put("friendsList", "");
            map.put("userEmail", email);
            allData.put("friends", map);
        } else {
            allData.put("friends", friends);
        }
    }

    private void addUsageData(String email, Map<String, Object> allData) {
        List<UsageDataEntity> usageDataEntities = usageDataRepository.findAll();
        List<UsageDataEntity> myUsageDataEntities = usageDataRepository.findByEmail(email);
        double totalMinutes = myUsageDataEntities.stream().mapToDouble(UsageDataEntity::getDuration).sum();
        Map<String, Double> usageDataMap = new HashMap<>();
        usageDataMap.put("totalMinutes", totalMinutes);
        allData.put("usageData", usageDataEntities);
        allData.put("totalMinutes", usageDataMap);
    }

    private void addScreenTimeData(String email, Map<String, Object> allData) {
        List<ScreenTimeEntity> screenTimeEntities = screenTimeRepository.findByEmail(email);
        allData.put("screenTime", screenTimeEntities);
    }

    private void addSomeTimesData(String email, Map<String, Object> allData) {
        List<SomeTimesEntity> someTimes = someTimesRepository.findByEmail(email);
        allData.put("someTimes", someTimes);
    }

    private void addDailyAverageUsageData(Map<String, Object> allData) {
        List<DailyAverageUsageStatisticsEntity> dailyAverageUsageStatistics = dailyAverageUsageStatisticsRepository.findAll();
        allData.put("dailyAverageUsage", dailyAverageUsageStatistics);
    }

    private void addMonthlyAverageUsageData(Map<String, Object> allData) {
        List<MonthlyAverageUsageStatisticsEntity> monthlyAverageUsageStatistics = monthlyAverageUsageStatisticsRepository.findAll();
        allData.put("monthlyAverageUsage", monthlyAverageUsageStatistics);
    }

    private void addUsageStatisticsData(Map<String, Object> allData) {
        List<UsageStatisticsEntity> usageStatistics = usageStatisticsRepository.findAll();
        allData.put("usageStatistics", usageStatistics);
    }

    private void addUserInfo(String email, Map<String, Object> allData) {
        UserEntity user = userRepository.findByemail(email);
        allData.put("userInfo", user);
    }

    private void addAllUsersData(Map<String, Object> allData) {
        List<UserEntity> allUsers = userRepository.findAll();
        allData.put("allUsers", allUsers);
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

        // 새 파일 생성
        if (!file.createNewFile()) {
            throw new IOException("Failed to create new file: " + filePath);
        }

        return file;
    }

/*
    public void exportFriendsListToJson(String filePath) throws IOException {
        String email = (String) httpSession.getAttribute("userEmail");
        FriendManagementEntity friends = friendManagementRepository.findByUserEmail(email);

        File file = saveJsonFile(filePath);

        if(friends == null) {
            Map<String, String> map = new HashMap<>();
            map.put("friendsList", "");
            map.put("userEmail", email);
            objectMapper.writeValue(file, map);
        } else {
            objectMapper.writeValue(file, friends);
        }
    }

    public void exportDataToJson(String filePath) throws IOException {
        String email = (String) httpSession.getAttribute("userEmail");
        List<UsageDataEntity> usageDataEntities = usageDataRepository.findByEmail(email);
        List<Map> list = new ArrayList<>();
        Map<String, Double> map = new HashMap<>();

        double n = 0;

        for (int i = 0; i < usageDataEntities.size(); i++) {
            n += usageDataEntities.get(i).getDuration();
        }

        map.put("totalMinutes", n);
        list.add(map);
        File file = saveJsonFile(filePath);

        objectMapper.writeValue(file, list);
    }

    // 로그인 한 유저의 Screen Time 정보
    public void exportScreenTimeToJson(String filePath) throws IOException {
        String email = (String) httpSession.getAttribute("userEmail");
        List<ScreenTimeEntity> screenTimeEntities = screenTimeRepository.findByEmail(email);

        File file = saveJsonFile(filePath);

        objectMapper.writeValue(file, screenTimeEntities);
    }

    // 로그인 한 유저의 Some Time 정보
    public void exportSomeTimeToJson(String filePath) throws IOException {
        String email = (String) httpSession.getAttribute("userEmail");
        List<SomeTimesEntity> someTimes = someTimesRepository.findByEmail(email);

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
        String email = (String) httpSession.getAttribute("userEmail");
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
*/
}
