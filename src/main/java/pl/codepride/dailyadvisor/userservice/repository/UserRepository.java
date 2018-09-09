package pl.codepride.dailyadvisor.userservice.repository;

import org.springframework.data.cassandra.repository.Query;
import pl.codepride.dailyadvisor.userservice.model.entity.User;

import java.util.List;
import java.util.UUID;


public interface UserRepository extends SimplyRepository<User> {

    @Query(value = "select * from user where email = ?0 allow filtering")
    User findByEmail(String email);



    @Query(value = "update User u set u.city = ?1, u.about = ?2, u.name = ?3, u.lastName = ?4 where u.id = ?0")
    void updateUserProfile(UUID id, String city, String about, String name, String lastName);

    List<User> findByCity(String city);
}
