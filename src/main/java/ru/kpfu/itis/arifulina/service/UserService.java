package ru.kpfu.itis.arifulina.service;

import ru.kpfu.itis.arifulina.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAllByName(String name);
}
