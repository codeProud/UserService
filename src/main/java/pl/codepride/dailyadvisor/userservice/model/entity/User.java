package pl.codepride.dailyadvisor.userservice.model.entity;


import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import pl.codepride.dailyadvisor.userservice.model.Role;
import pl.codepride.dailyadvisor.userservice.model.request.NewUserRequest;

import java.util.Objects;
import java.util.UUID;


@Data
@Table
public class User {

    @PrimaryKey
    private UUID id;

    private String email;
    private String password;
    private boolean active;
    private String role;
    private boolean enabled;


    public User(NewUserRequest newUserRequest) {
        this.setPassword(newUserRequest.getPassword());
        this.setEmail(newUserRequest.getEmail());
        this.setActive(false);
        this.enabled = false;
        this.role = Role.USER.toString();
    }

    public User() {
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isActive() == user.isActive() &&
                isEnabled() == user.isEnabled() &&
                Objects.equals(getEmail(), user.getEmail()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                Objects.equals(getRole(), user.getRole());
    }
}
