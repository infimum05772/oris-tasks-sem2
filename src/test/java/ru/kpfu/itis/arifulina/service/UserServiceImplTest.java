package ru.kpfu.itis.arifulina.service;

import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.kpfu.itis.arifulina.base.Constants;
import ru.kpfu.itis.arifulina.base.ParamsKey;
import ru.kpfu.itis.arifulina.config.MailConfig;
import ru.kpfu.itis.arifulina.dto.CreateUserRequestDto;
import ru.kpfu.itis.arifulina.dto.UserDto;
import ru.kpfu.itis.arifulina.mapper.impl.UserToUserDtoMapper;
import ru.kpfu.itis.arifulina.model.Role;
import ru.kpfu.itis.arifulina.model.User;
import ru.kpfu.itis.arifulina.repository.RoleRepository;
import ru.kpfu.itis.arifulina.repository.UserRepository;
import ru.kpfu.itis.arifulina.util.TestConstants;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceImplTest {

    public static final String DEFAULT_ENCODING = StandardCharsets.UTF_8.name();
    public static final String BAD_ENCODING = "BAD-ENCODING";

    private static UserService userService;

    @MockBean
    private static UserRepository userRepository;

    @MockBean
    private static RoleRepository roleRepository;

    @Autowired
    private UserToUserDtoMapper userToUserDtoMapper;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailConfig mailConfig;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void initService() {
        ((JavaMailSenderImpl) javaMailSender).setDefaultEncoding(DEFAULT_ENCODING);
        userService = new UserServiceImpl(
                userRepository,
                mailConfig,
                javaMailSender,
                passwordEncoder,
                roleRepository,
                userToUserDtoMapper
        );
    }

    @Test
    public void testFindAllByName() {
        given(userRepository.findAllByName(TestConstants.DEFAULT_USERNAME))
                .willReturn(Collections.singletonList(User.builder().name(TestConstants.DEFAULT_USERNAME).build()));

        Assertions.assertEquals(
                Collections.singletonList(new UserDto(TestConstants.DEFAULT_USERNAME)),
                userService.findAllByName(TestConstants.DEFAULT_USERNAME)
        );
        verify(userRepository, times(1)).findAllByName(TestConstants.DEFAULT_USERNAME);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testCreate() {
        CreateUserRequestDto dto = new CreateUserRequestDto(
                TestConstants.DEFAULT_USERNAME,
                TestConstants.DEFAULT_EMAIL,
                TestConstants.DEFAULT_PASSWORD
        );
        Role userRole = Role.builder().name(ParamsKey.AUTHORITY_USER).build();
        User user = User.builder().name(TestConstants.DEFAULT_USERNAME).build();
        UserDto userDto = new UserDto(TestConstants.DEFAULT_USERNAME);

        given(roleRepository.findByName(ParamsKey.AUTHORITY_USER))
                .willReturn(Optional.of(userRole));
        given(userRepository.save(any(User.class))).willReturn(user);

        Assertions.assertEquals(userDto, userService.create(dto, TestConstants.DEFAULT_BASE_URL));
        verify(userRepository, times(1)).save(any(User.class));
        verify(roleRepository, times(1)).findByName(ParamsKey.AUTHORITY_USER);
        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(roleRepository);
    }

    @Test
    public void testCreateFail() {
        ((JavaMailSenderImpl) javaMailSender).setDefaultEncoding(BAD_ENCODING);
        CreateUserRequestDto dto = new CreateUserRequestDto(
                TestConstants.DEFAULT_USERNAME,
                TestConstants.DEFAULT_EMAIL,
                TestConstants.DEFAULT_PASSWORD
        );

        Assertions.assertThrows(RuntimeException.class, () -> userService.create(dto, TestConstants.DEFAULT_BASE_URL));
    }

    @Test
    public void testVerify() {
        verifyUser(true);
    }

    @Test
    public void testVerifyFail() {
        verifyUser(false);
    }

    private void verifyUser(boolean success) {
        String code = RandomString.make(Constants.VERIFICATION_CODE_LENGTH);
        User user = new User();

        given(userRepository.findByVerificationCode(code)).willReturn(success ? Optional.of(user) : Optional.empty());
        given(userRepository.save(any(User.class))).willReturn(user);

        Assertions.assertEquals(success, userService.verify(code));
        verify(userRepository, times(1)).findByVerificationCode(code);
        verify(userRepository, times(success ? 1 : 0)).save(any(User.class));
        verifyNoMoreInteractions(userRepository);
    }
}
