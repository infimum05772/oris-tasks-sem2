package ru.kpfu.itis.arifulina.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.arifulina.aspect.annotation.HttpRequest;
import ru.kpfu.itis.arifulina.aspect.annotation.Loggable;
import ru.kpfu.itis.arifulina.base.Messages;
import ru.kpfu.itis.arifulina.base.ParamsKey;

@RestController
public class HelloController {
    @GetMapping(ParamsKey.HELLO_RM)
    @Loggable
    @HttpRequest
    public String hello() {
        return Messages.HELLO_MSG;
    }
}
