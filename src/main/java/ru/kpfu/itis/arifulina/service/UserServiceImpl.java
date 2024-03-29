package ru.kpfu.itis.arifulina.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.arifulina.dto.UserDto;
import ru.kpfu.itis.arifulina.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    @Override
    public List<UserDto> findAllByName(String name) {
        return userRepository.findAllByName(name)
                .stream()
                .map(user -> new UserDto(name))
                .collect(Collectors.toList());
    }
}
