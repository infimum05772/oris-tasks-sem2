package ru.kpfu.itis.arifulina.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import ru.kpfu.itis.arifulina.base.Constants;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password", length = Constants.PASSWORD_MAX_LENGTH, nullable = false)
    private String password;

    @Column(name = "verification_code", length = Constants.VERIFICATION_CODE_LENGTH)
    private String verificationCode;

    @Column(name = "enabled")
    @ColumnDefault("false")
    private boolean enabled;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles;
}
