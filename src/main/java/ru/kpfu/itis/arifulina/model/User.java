package ru.kpfu.itis.arifulina.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import ru.kpfu.itis.arifulina.base.Constants;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return enabled == user.enabled && Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(verificationCode, user.verificationCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, username, password, verificationCode, enabled);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
