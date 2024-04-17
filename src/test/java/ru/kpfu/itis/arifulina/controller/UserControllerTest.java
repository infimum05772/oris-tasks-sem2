package ru.kpfu.itis.arifulina.controller;

import com.google.gson.Gson;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.kpfu.itis.arifulina.base.Constants;
import ru.kpfu.itis.arifulina.base.ParamsKey;
import ru.kpfu.itis.arifulina.config.SecurityConfig;
import ru.kpfu.itis.arifulina.dto.CreateUserRequestDto;
import ru.kpfu.itis.arifulina.dto.UserDto;
import ru.kpfu.itis.arifulina.service.UserService;
import ru.kpfu.itis.arifulina.util.TestConstants;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@ExtendWith(SpringExtension.class)
@Import(SecurityConfig.class)
public class UserControllerTest {

    public static final String VERIFICATION_SUCCESS_MSG = "VERIFICATION SUCCESS";
    public static final String VERIFICATION_FAILED_MSG = "VERIFICATION FAILED";
    public static final String SIGN_UP_SUCCESS_MSG = "SIGN UP SUCCESS";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private static Gson gson;

    @BeforeAll
    public static void beforeAll() {
        gson = new Gson();
    }

    @Test
    public void testGetUser() throws Exception {
        String name = "a";
        UserDto userDto = new UserDto(name);
        given(userService.findAllByName(name)).willReturn(List.of(userDto));

        mockMvc.perform(get(ParamsKey.USERS_RM + "?" + ParamsKey.NAME_PARAM + "=" + name)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user(TestConstants.DEFAULT_USERNAME).roles(ParamsKey.ROLE_ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value(name));
    }

    @Test
    public void testCreateUser() throws Exception {
        CreateUserRequestDto dto = new CreateUserRequestDto(
                TestConstants.DEFAULT_USERNAME,
                TestConstants.DEFAULT_EMAIL,
                TestConstants.DEFAULT_PASSWORD
        );
        given(userService.create(dto, TestConstants.DEFAULT_BASE_URL)).willReturn(new UserDto(dto.getName()));

        mockMvc.perform(post(ParamsKey.USER_RM)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(dto))
                        .with(anonymous()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(content().string(containsString(SIGN_UP_SUCCESS_MSG)));
    }

    @Test
    public void testVerificationSuccess() throws Exception {
        verification(true, VERIFICATION_SUCCESS_MSG);
    }

    @Test
    public void testVerificationFailed() throws Exception {
        verification(false, VERIFICATION_FAILED_MSG);
    }

    private void verification(boolean success, String result) throws Exception {
        String code = RandomString.make(Constants.VERIFICATION_CODE_LENGTH);
        given(userService.verify(code)).willReturn(success);

        mockMvc.perform(get(ParamsKey.VERIFICATION_RM + "?" + ParamsKey.CODE_PARAM + "=" + code)
                        .with(anonymous()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(content().string(containsString(result)));
    }
}
