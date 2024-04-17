package ru.kpfu.itis.arifulina.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.kpfu.itis.arifulina.base.ParamsKey;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers(ParamsKey.SIGN_UP_RM, ParamsKey.SIGN_IN_RM, ParamsKey.USER_RM).anonymous()
                .antMatchers(ParamsKey.PROFILE_RM, ParamsKey.EXCHANGE_RATES_RM + "/**",
                        ParamsKey.KAZAN_WEATHER_RM + "/**").hasAnyRole(ParamsKey.ROLE_USER, ParamsKey.ROLE_ADMIN)
                .antMatchers(ParamsKey.ADMIN_RM + "/**").hasRole(ParamsKey.ROLE_ADMIN);

        return httpSecurity.csrf().disable()
                .formLogin()
                .loginPage(ParamsKey.SIGN_IN_RM)
                .loginProcessingUrl(ParamsKey.LOGIN_PROCESSING_RM)
                .usernameParameter(ParamsKey.USERNAME_PARAM)
                .passwordParameter(ParamsKey.PASSWORD_PARAM)
                .defaultSuccessUrl(ParamsKey.PROFILE_RM, true)
                .failureUrl(ParamsKey.LOGIN_FAILURE_RM)
                .and()
                .logout()
                .logoutSuccessUrl(ParamsKey.SIGN_IN_RM)
                .and()
                .exceptionHandling()
                .and().build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
