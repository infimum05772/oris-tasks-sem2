package ru.kpfu.itis.arifulina.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.arifulina.aspect.annotation.HttpRequest;
import ru.kpfu.itis.arifulina.aspect.annotation.Loggable;
import ru.kpfu.itis.arifulina.base.ParamsKey;
import ru.kpfu.itis.arifulina.dto.CreateUserRequestDto;
import ru.kpfu.itis.arifulina.dto.UserDto;
import ru.kpfu.itis.arifulina.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Loggable
    @GetMapping(value = ParamsKey.USERS_RM)
    @HttpRequest
    @ResponseBody
    public List<UserDto> findUsersByName(@RequestParam(ParamsKey.NAME_PARAM) String name) {
        return userService.findAllByName(name);
    }

    @Loggable
    @HttpRequest
    @PostMapping(value = ParamsKey.USER_RM)
    public String create(@ModelAttribute CreateUserRequestDto user,
                         HttpServletRequest request) {
        String url = request.getRequestURL().toString().replace(request.getServletPath(), "");
        userService.create(user, url);
        return ParamsKey.SIGN_UP_SUCCESS_VN;
    }

    @Loggable
    @HttpRequest
    @GetMapping(ParamsKey.VERIFICATION_RM)
    public String verify(@RequestParam(ParamsKey.CODE_PARAM) String code) {
        if (userService.verify(code)) {
            return ParamsKey.VERIFICATION_SUCCESS_VN;
        }
        return ParamsKey.VERIFICATION_FAILED_VN;
    }
}
