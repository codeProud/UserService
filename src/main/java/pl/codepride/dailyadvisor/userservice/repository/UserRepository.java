package pl.codepride.dailyadvisor.userservice.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import pl.codepride.dailyadvisor.userservice.model.entity.User;

import java.util.UUID;


@Repository("userRepository")
public interface UserRepository extends CassandraRepository<User, UUID> {

    User findByEmail(String email);

}
