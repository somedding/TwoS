package test.test_Internet.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    private ObjectMapper objectMapper;

    @Autowired
    private HttpSession httpSession;

    public DataToJsonService(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public void exportUserInfoToJsonFile(String filePath) throws IOException {
        String email = (String) httpSession.getAttribute("userEmail");
        UserEntity users = userRepository.findByemail(email);

        File file = saveJsonFile(filePath);

        objectMapper.writeValue(file, users);
    }

    public void exportUsersToJsonFile(String filePath) throws IOException {
        // 데이터베이스에서 모든 사용자 정보를 가져옵니다.
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
