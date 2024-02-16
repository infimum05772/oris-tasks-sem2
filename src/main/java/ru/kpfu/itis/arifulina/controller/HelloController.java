package ru.kpfu.itis.arifulina.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/itis")
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "HELLO";
    }
}
