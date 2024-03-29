package ru.kpfu.itis.arifulina.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.arifulina.model.User;
import ru.kpfu.itis.arifulina.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthProvider implements AuthenticationProvider {

    private final static PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
    private final UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        log.info(username);
        User user = userRepository.findByUsername(username).orElseThrow(IllegalArgumentException::new);

        log.info(user.toString());
        String password = authentication.getCredentials().toString();
        if (!PASSWORD_ENCODER.matches(password, user.getPassword())) {
            log.warn("AAAAA");
            throw new BadCredentialsException("Invalid Password");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(
                role -> {
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
                }
        );

        return new UsernamePasswordAuthenticationToken(user, null, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
