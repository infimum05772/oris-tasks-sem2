package ru.kpfu.itis.arifulina.mapper.impl;

import org.springframework.stereotype.Component;
import ru.kpfu.itis.arifulina.dto.UserDto;
import ru.kpfu.itis.arifulina.mapper.Mapper;
import ru.kpfu.itis.arifulina.model.User;

@Component
public class UserToUserDtoMapper implements Mapper<User, UserDto> {
    @Override
    public UserDto map(User user) {
        return new UserDto(user.getName());
    }
}
