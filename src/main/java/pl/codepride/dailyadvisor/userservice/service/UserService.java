package pl.codepride.dailyadvisor.userservice.service;

import pl.codepride.dailyadvisor.userservice.model.Role;
import pl.codepride.dailyadvisor.userservice.model.entity.User;
import pl.codepride.dailyadvisor.userservice.model.request.NewUserRequest;
import pl.codepride.dailyadvisor.userservice.service.Exceptions.DataRepositoryException;


import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface UserService extends IService<User, UUID> {

	User findUserByEmail(String email);

	void registerClient(NewUserRequest newUserRequest);

	User registerOauth2User(String email);

	Optional<User> findById(UUID userId);

	void registerCoach(NewUserRequest newUserRequest);

	void registerUser(NewUserRequest newUserRequest, List<Role> roles);

	void upgradeUserToCoach(User userId);

	User enableUser(UUID userId) throws DataRepositoryException;

	boolean confirmRegistration(String token) throws DataRepositoryException;
}
