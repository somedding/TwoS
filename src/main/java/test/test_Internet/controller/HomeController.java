package test.test_Internet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/sweetodo/todo/todoMain")
    public String home(@RequestParam(required = false) String email) {
        String redirectUrl = "main";
        if (email != null) {
            redirectUrl += "?email=" + email; // 이메일 파라미터 추가
        }
        return redirectUrl;
    }

    @GetMapping("/")
    public String index(@RequestParam(required = false) String email) {
        String redirectUrl = "index";
        if (email != null) {
            redirectUrl += "?email=" + email; // 이메일 파라미터 추가
        }
        return redirectUrl;
    }

    @GetMapping("/error")
    public String error(@RequestParam(required = false) String email) {
        String redirectUrl = "index";
        if (email != null) {
            redirectUrl += "?email=" + email; // 이메일 파라미터 추가
        }
        return redirectUrl;
    }

    @GetMapping("/todo/community")
    public String community(@RequestParam(required = false) String email) {
        String redirectUrl = "community";
        if (email != null) {
            redirectUrl += "?email=" + email; // 이메일 파라미터 추가
        }
        return redirectUrl;
    }

    @GetMapping("/todo/data")
    public String todoData(@RequestParam(required = false) String email) {
        String redirectUrl = "graph";
        if (email != null) {
            redirectUrl += "?email=" + email; // 이메일 파라미터 추가
        }
        return redirectUrl;
    }

    @GetMapping("/unable/home")
    public String unableHome(@RequestParam(required = false) String email) {
        String redirectUrl = "index";
        if (email != null) {
            redirectUrl += "?email=" + email; // 이메일 파라미터 추가
        }
        return redirectUrl;
    }
}
