package pl.codepride.dailyadvisor.userservice.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;
import pl.codepride.dailyadvisor.userservice.model.entity.User;

import java.util.UUID;


public interface UserRepository extends CassandraRepository<User, UUID> {

    @Query(value = "select * from user where email = ?0 allow filtering")
    User findByEmail(String email);

}
