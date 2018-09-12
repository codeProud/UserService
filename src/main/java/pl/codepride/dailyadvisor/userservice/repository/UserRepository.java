package pl.codepride.dailyadvisor.userservice.repository;

import org.springframework.data.cassandra.repository.Query;
import pl.codepride.dailyadvisor.userservice.model.entity.User;

import java.util.List;
import java.util.UUID;


public interface UserRepository extends SimplyRepository<User> {

    @Query(value = "select * from user where email = ?0 allow filtering")
    User findByEmail(String email);

}
