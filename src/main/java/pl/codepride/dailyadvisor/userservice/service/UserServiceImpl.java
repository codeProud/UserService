package pl.codepride.dailyadvisor.userservice.service;

import pl.codepride.dailyadvisor.userservice.model.Role;
import pl.codepride.dailyadvisor.userservice.model.entity.User;
import pl.codepride.dailyadvisor.userservice.model.request.NewUserRequest;
import pl.codepride.dailyadvisor.userservice.model.request.UserProfileRequest;
import pl.codepride.dailyadvisor.userservice.model.response.UserProfileResponse;
import pl.codepride.dailyadvisor.userservice.repository.UserRepository;
import pl.codepride.dailyadvisor.userservice.service.Exceptions.DataRepositoryException;
import pl.codepride.dailyadvisor.userservice.service.Exceptions.EntityExists;
import pl.codepride.dailyadvisor.userservice.service.Exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("userService")
public class UserServiceImpl implements UserService {

    private static final String MESSAGE_NOT_FOUND_MESSAGE_CODE = "exception.entityNotFoundException.meal";

    private static final String MESSAGE_EXISTS_MESSAGE_CODE = "exception.entityNotFoundException.meal";

    @Autowired
    @Qualifier("userRepository")
    private UserRepository repository;

    @Autowired
    public VerificationTokenService verificationTokenService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User create(User user) throws EntityExists {
        if (user.getId() == null || !repository.existsById(user.getId())) {
            return repository.save(user);
        } else {
            throw new EntityExists(MESSAGE_EXISTS_MESSAGE_CODE);
        }
    }

    @Override
    public void delete(UUID id) throws DataRepositoryException {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new EntityNotFoundException(MESSAGE_NOT_FOUND_MESSAGE_CODE);
        }
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<User> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public User update(User user) throws DataRepositoryException, NoSuchElementException {
        if (repository.existsById(user.getId())) {
            return repository.save(user);
        } else {
            throw new EntityNotFoundException(MESSAGE_NOT_FOUND_MESSAGE_CODE);
        }
    }

    @Override
    public User findUserByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public void registerClient(NewUserRequest newUserRequest) {
        String[] roles = {Role.USER.toString()};
        registerUser(newUserRequest, Arrays.asList(roles));
    }

    @Override
    public void registerCoach(NewUserRequest newUserRequest) {
        String[] roles = {Role.COACH.toString()};
        registerUser(newUserRequest, Arrays.asList(roles));
    }

    @Override
    public void registerUser(NewUserRequest newUserRequest, List<String> roles) {
        User user = new User(newUserRequest);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        repository.save(user);
    }

    @Override
    public UserProfileResponse createUserProfileResponseByUser(User user) {
        return new UserProfileResponse(user);
    }

    @Override
    public void updateUserProfile(UserProfileRequest userProfileRequest, UUID userId) {
        repository.updateUserProfile(userId, userProfileRequest.getCity(), userProfileRequest.getAbout(), userProfileRequest.getName(), userProfileRequest.getLastName());
    }

    @Override
    public void upgradeUserToCoach(User user) {
        if (!user.getRoles().contains(Role.COACH.toString())) {
            user.getRoles().add(Role.COACH.toString());
            repository.save(user);
        }
    }

    @Override
    public List<User> findByCity(String city) {
        return repository.findByCity(city);
    }

    @Override
    public User enableUser(UUID userId) throws DataRepositoryException {
        User user = repository.findById(userId).get();
        user.setEnabled(true);
        update(user);
        return user;
    }

    @Override
    public boolean confirmRegistration(String token) throws DataRepositoryException {
        UUID userId = verificationTokenService.confirmToken(token);
        enableUser(userId);
        return true;
    }

    @Override
    public User registerOauth2User(String email) {
        User user = new User();
        user.setEmail(email);
        user.setEnabled(true);
        user.setActive(true);
        String[] roles = {Role.USER.toString()};
        user.setRoles(Arrays.asList(roles));
        return repository.save(user);
    }

}
