package ru.kpfu.itis.arifulina.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.arifulina.aspect.annotation.Cacheable;
import ru.kpfu.itis.arifulina.aspect.annotation.Loggable;
import ru.kpfu.itis.arifulina.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Loggable
    @Cacheable
    List<User> findAllByName(String name);

    @Loggable
    @Cacheable
    @Query("select u from User u join fetch u.roles where u.username=:username")
    Optional<User> findByUsername(@Param("username") String username);

    @Loggable
    @Cacheable
    @Query("select u from User u join fetch u.roles where u.verificationCode=:verificationCode")
    Optional<User> findByVerificationCode(@Param("verificationCode") String verificationCode);
}
