package ru.kpfu.itis.arifulina.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/")
    public String welcome() {
        return "welcum";
    }
    @GetMapping("/hello")
    public String hello() {
        return "HELLO";
    }
}
