package pl.codepride.dailyadvisor.userservice.model.entity;


import com.datastax.driver.core.utils.UUIDs;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import pl.codepride.dailyadvisor.userservice.model.Role;
import pl.codepride.dailyadvisor.userservice.model.request.NewUserRequest;

import java.util.ArrayList;
import java.util.List;
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
    private List<Role> roles;
    private boolean enabled;
    private String name;

    @Column(value = "last_name")
    private String lastName;


    public User(NewUserRequest newUserRequest) {
        this.id = UUIDs.timeBased();
        this.setPassword(newUserRequest.getPassword());
        this.setEmail(newUserRequest.getEmail());
        this.setActive(false);
        this.name = newUserRequest.getName();
        this.lastName = newUserRequest.getLastName();
        this.enabled = false;
        this.roles = new ArrayList<>();
        this.roles.add(Role.USER);
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
                Objects.equals(getRoles(), user.getRoles()) &&
                Objects.equals(getName(), user.getName()) &&
                Objects.equals(getLastName(), user.getLastName());
    }
}
