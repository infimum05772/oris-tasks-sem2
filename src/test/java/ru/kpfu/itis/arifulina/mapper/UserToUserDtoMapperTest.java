package ru.kpfu.itis.arifulina.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.kpfu.itis.arifulina.dto.UserDto;
import ru.kpfu.itis.arifulina.mapper.impl.UserToUserDtoMapper;
import ru.kpfu.itis.arifulina.model.User;
import ru.kpfu.itis.arifulina.util.TestConstants;

public class UserToUserDtoMapperTest {

    private static Mapper<User, UserDto> mapper;

    @BeforeAll
    public static void init() {
        mapper = new UserToUserDtoMapper();
    }

    @Test
    public void testMap() {
        User user = User.builder().name(TestConstants.DEFAULT_USERNAME).build();
        UserDto userDto = new UserDto(TestConstants.DEFAULT_USERNAME);

        Assertions.assertEquals(userDto, mapper.map(user));
    }
}
