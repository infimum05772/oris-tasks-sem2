package ru.kpfu.itis.arifulina.service;

import ru.kpfu.itis.arifulina.dto.CreateUserRequestDto;
import ru.kpfu.itis.arifulina.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAllByName(String name);

    UserDto create(CreateUserRequestDto dto, String url);

    boolean verify(String code);

    void sendVerificationCode(String mail, String name, String code, String baseUrl);
}
