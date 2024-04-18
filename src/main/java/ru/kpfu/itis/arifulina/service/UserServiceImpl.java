package ru.kpfu.itis.arifulina.service;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final MailConfig mailConfig;
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserToUserDtoMapper userToUserDtoMapper;

    @Override
    public List<UserDto> findAllByName(String name) {
        return userRepository.findAllByName(name)
                .stream()
                .map(userToUserDtoMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto create(CreateUserRequestDto dto, String url) {
        String code = RandomString.make(Constants.VERIFICATION_CODE_LENGTH);
        sendVerificationCode(dto.getEmail(), dto.getName(), code, url);

        Role userRole = roleRepository
                .findByName(ParamsKey.AUTHORITY_USER)
                .orElseThrow(NoSuchElementException::new);
        HashSet<Role> roles = new HashSet<>();
        roles.add(userRole);

        return userToUserDtoMapper.map(userRepository.save(User.builder()
                .name(dto.getName())
                .roles(roles)
                .password(passwordEncoder.encode(dto.getPassword()))
                .verificationCode(code)
                .username(dto.getEmail()).build()));
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
            content = content.replace(Constants.EMAIL_NAME_REPLACING, name);
            content = content.replace(Constants.EMAIL_URL_REPLACING,
                    baseUrl + ParamsKey.VERIFICATION_RM + "?" + ParamsKey.CODE_PARAM + "=" + code);
            helper.setText(content);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException | UnsupportedEncodingException | MailException e) {
            throw new RuntimeException(e);
        }
    }
}
