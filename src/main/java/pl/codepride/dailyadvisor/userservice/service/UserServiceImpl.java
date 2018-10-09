package pl.codepride.dailyadvisor.userservice.service;

import com.datastax.driver.core.utils.UUIDs;
import pl.codepride.dailyadvisor.userservice.model.Role;
import pl.codepride.dailyadvisor.userservice.model.entity.User;
import pl.codepride.dailyadvisor.userservice.model.request.NewUserRequest;
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
        Role[] roles = {Role.USER};
        registerUser(newUserRequest, Arrays.asList(roles));
    }

    @Override
    public void registerCoach(NewUserRequest newUserRequest) {
        Role[] roles = {Role.COACH};
        registerUser(newUserRequest, Arrays.asList(roles));
    }

    @Override
    public void registerUser(NewUserRequest newUserRequest, List<Role> roles) {
        User user = new User(newUserRequest);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        repository.save(user);
    }

    @Override
    public void upgradeUserToCoach(User user) {
        if (!user.getRoles().contains(Role.COACH)) {
            user.getRoles().add(Role.COACH);
            repository.save(user);
        }
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
        user.setId(UUIDs.timeBased());
        Role[] roles = {Role.USER};
        user.setRoles(Arrays.asList(roles));
        return repository.save(user);
    }

}
