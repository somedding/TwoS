package test.test_Internet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class HomeController {
    @GetMapping("/sweetodo/todo/todoMain")
    public String home() {
        return "main";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/error")
    public String error() {
        return "index";
    }

    @GetMapping("/todo/community")
    public String community() {
        return "community";
    }

    @GetMapping("/todo/data")
    public String todoData() {
        return "graph";
    }

    @GetMapping("/unable/community")
    public String unableCommunity() { return "LoginCommunity"; }

    @GetMapping("/unable/home")
    public String unableHome() { return "index"; }

    @GetMapping("/unable/data")
    public String unableData() { return "Logindata"; }
}
