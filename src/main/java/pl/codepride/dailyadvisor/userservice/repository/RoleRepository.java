package pl.codepride.dailyadvisor.userservice.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import pl.codepride.dailyadvisor.userservice.model.entity.Role;

import java.util.UUID;

@Repository("roleRepository")
public interface RoleRepository extends CassandraRepository<Role, UUID> {
	Role findByRole(String role);
}
