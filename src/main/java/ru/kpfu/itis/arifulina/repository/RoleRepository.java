package ru.kpfu.itis.arifulina.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.arifulina.aspect.annotation.Cacheable;
import ru.kpfu.itis.arifulina.aspect.annotation.Loggable;
import ru.kpfu.itis.arifulina.model.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Loggable
    @Cacheable
    Optional<Role> findByName(String name);
}
