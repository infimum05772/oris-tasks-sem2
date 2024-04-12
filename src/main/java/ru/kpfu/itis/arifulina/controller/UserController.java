package ru.kpfu.itis.arifulina.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.arifulina.dto.CreateUserRequestDto;
import ru.kpfu.itis.arifulina.dto.UserDto;
import ru.kpfu.itis.arifulina.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(value = "/users")
    @ResponseBody
    public List<UserDto> findUsersByName(@RequestParam String name) {
        return userService.findAllByName(name);
    }

    @PostMapping(value = "/user")
    public String create(@ModelAttribute CreateUserRequestDto user,
                         HttpServletRequest request) {
        String url = request.getRequestURL().toString().replace(request.getServletPath(), "");
        userService.create(user, url);
        return "sign_up_success";
    }

    @GetMapping("/verification")
    public String verify(@RequestParam("code") String code) {
        if (userService.verify(code)) {
            return "verification_success";
        }
        return "verification_failed";
    }
}
