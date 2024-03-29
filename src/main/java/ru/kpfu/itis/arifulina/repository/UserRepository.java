package ru.kpfu.itis.arifulina.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.arifulina.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //1
    List<User> findAllByName(String name);
    //2 JPQL
    @Query(value = "select u from User u where u.name = :name")
    List<User> findAllByNameJPQL(String name);
    //3 SQL
    @Query(value = "select * from users u where u.name = ?1;", nativeQuery = true)
    List<User> findAllByNameNative(String name);

    Optional<User> findByUsername(String username);
}
