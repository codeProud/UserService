package pl.codepride.dailyadvisor.userservice.model.entity;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Objects;
import java.util.UUID;

@Data
@Table
public class Role {

    @PrimaryKey
	private UUID id;
	private String role;


	public Role(String role) {
		this.role = role;
	}

	public Role() {
	}

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role1 = (Role) o;
        return Objects.equals(getRole(), role1.getRole());
    }
}
