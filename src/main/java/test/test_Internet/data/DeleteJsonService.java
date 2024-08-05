package test.test_Internet.data;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class DeleteJsonService {

    public void deleteFileIfExists(String filePath) {
        Path path = Paths.get(filePath);
        try {
            if (Files.exists(path)) {
                Files.delete(path);
                System.out.println("Deleted file: " + path);
            } else {
                System.out.println("File does not exist: " + path);
            }
        } catch (IOException e) {
            System.err.println("An error occurred while deleting the file: " + e.getMessage());
        }
    }

}
