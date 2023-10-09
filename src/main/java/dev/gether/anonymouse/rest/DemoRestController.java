package dev.gether.anonymouse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoRestController {

    @GetMapping("/")
    public String getTest() {
        return "123";
    }
}
