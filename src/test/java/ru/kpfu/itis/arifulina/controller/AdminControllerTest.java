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
import ru.kpfu.itis.arifulina.util.TestConstants;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
@ExtendWith(SpringExtension.class)
@Import(SecurityConfig.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAdminByAdmin() throws Exception {
        mockMvc.perform(get(ParamsKey.ADMIN_RM)
                        .with(user(TestConstants.DEFAULT_USERNAME).roles(ParamsKey.ROLE_ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string(Messages.ADMIN_MSG));
    }

    @Test
    public void testAdminByUser() throws Exception {
        mockMvc.perform(get(ParamsKey.ADMIN_RM)
                        .with(user(TestConstants.DEFAULT_USERNAME).roles(ParamsKey.ROLE_USER)))
                .andExpect(status().isForbidden());
    }
}
