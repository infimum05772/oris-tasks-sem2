package ru.kpfu.itis.arifulina.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.arifulina.base.Messages;
import ru.kpfu.itis.arifulina.base.ParamsKey;

@RestController
@RequestMapping(ParamsKey.ADMIN_RM)
public class AdminController {

    @GetMapping
    public String admin() {
        return Messages.ADMIN_MSG;
    }
}
