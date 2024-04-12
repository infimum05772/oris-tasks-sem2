package ru.kpfu.itis.arifulina.service;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.arifulina.config.MailConfig;
import ru.kpfu.itis.arifulina.dto.CreateUserRequestDto;
import ru.kpfu.itis.arifulina.dto.UserDto;
import ru.kpfu.itis.arifulina.model.User;
import ru.kpfu.itis.arifulina.repository.UserRepository;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final MailConfig mailConfig;
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> findAllByName(String name) {
        return userRepository.findAllByName(name)
                .stream()
                .map(user -> new UserDto(name))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto create(CreateUserRequestDto dto, String url) {
        String code = RandomString.make(128);
        sendVerificationCode(dto.getEmail(), dto.getName(), code, url);
        return new UserDto(userRepository.save(
                User.builder()
                        .name(dto.getName())
                        .password(passwordEncoder.encode(dto.getPassword()))
                        .verificationCode(code)
                        .username(dto.getEmail()).build()
        ).getName());
    }

    @Override
    public boolean verify(String code) {
        Optional<User> user = userRepository.findByVerificationCode(code);
        if (user.isPresent()) {
            User u = user.get();
            u.setVerificationCode(null);
            u.setEnabled(true);
            userRepository.save(u);
        }
        return user.isPresent();
    }


    @Override
    public void sendVerificationCode(String mail, String name, String code, String baseUrl) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        String content = mailConfig.getContent();

        try {
            helper.setFrom(mailConfig.getFrom(), mailConfig.getSender());
            helper.setTo(mail);
            helper.setSubject(mailConfig.getSubject());
            content = content.replace("{name}", name);
            content = content.replace("{url}", baseUrl + "/verification?code=" + code);
            helper.setText(content);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }


    }
}
