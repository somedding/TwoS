package test.test_Internet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DataController {

    @GetMapping("/data")
    public ResponseEntity<String> getData() {
        // 필요한 로직을 추가하여 데이터 반환
        String jsonData = "{\"key\":\"value\"}"; // 실제 JSON 데이터
        return ResponseEntity.ok(jsonData);
    }
}

