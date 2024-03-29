package ru.kpfu.itis.arifulina.repository;

import ru.kpfu.itis.arifulina.model.User;

import java.util.List;

public interface CustomUserRepository {
    List<User> findAllByNameMatch(String name, double factor);
}
