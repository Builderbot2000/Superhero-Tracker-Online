package ca.sfu.cmpt213.assignment3;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrackerController {

    @GetMapping("/hello")
    public String getHelloMessage() {
        return "Hello Spring Boot World!";
    }
}
