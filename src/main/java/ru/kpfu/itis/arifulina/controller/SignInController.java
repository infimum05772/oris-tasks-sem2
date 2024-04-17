package ru.kpfu.itis.arifulina.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kpfu.itis.arifulina.base.Messages;
import ru.kpfu.itis.arifulina.base.ParamsKey;

@Controller
@RequestMapping(ParamsKey.SIGN_IN_RM)
public class SignInController {

    @GetMapping
    public String signIn(Model model, @RequestParam(required = false, name = ParamsKey.AUTH_FAIL_KEY) boolean authFail) {
        if (authFail) {
            model.addAttribute(ParamsKey.AUTH_FAIL_KEY, Messages.AUTH_FAIL_MSG);
        }
        return ParamsKey.SIGN_IN_VN;
    }
}
