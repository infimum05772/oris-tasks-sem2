package ru.kpfu.itis.arifulina.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.kpfu.itis.arifulina.base.Messages;
import ru.kpfu.itis.arifulina.base.ParamsKey;
import ru.kpfu.itis.arifulina.config.SecurityConfig;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignInController.class)
@ExtendWith(SpringExtension.class)
@Import(SecurityConfig.class)
public class SignInControllerTest {

    public static final String LOGIN_PAGE_TITLE = "Login page";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testSignIn() throws Exception {
        mockMvc.perform(get(ParamsKey.SIGN_IN_RM)
                        .with(anonymous()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(content().string(containsString(LOGIN_PAGE_TITLE)))
                .andExpect(content().string(not(containsString(Messages.AUTH_FAIL_MSG))));
    }

    @Test
    public void testSignInError() throws Exception {
        mockMvc.perform(get(ParamsKey.LOGIN_FAILURE_RM)
                        .with(anonymous()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(content().string(containsString(LOGIN_PAGE_TITLE)))
                .andExpect(content().string(containsString(Messages.AUTH_FAIL_MSG)));
    }
}
