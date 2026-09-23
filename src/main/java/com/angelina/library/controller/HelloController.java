package com.angelina.library.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Spring!";
    }

    @GetMapping("/hello/{name}")
    public String helloName(@PathVariable String name) {
        return "Hello, " + name + "!";
    }

    @GetMapping("/greet")
    public String greet(@RequestParam(defaultValue =  "friend") String name) {
        return "Hi " + name;
    }

    @GetMapping("/ping")
    public Map<String, Object> ping() {
        return Map.of("status", "ok", "service", "library");
    }
}

