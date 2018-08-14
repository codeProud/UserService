package pl.codepride.dailyadvisor.userservice.service;

import pl.codepride.dailyadvisor.userservice.model.Role;
import pl.codepride.dailyadvisor.userservice.model.entity.User;
//import pl.codepride.dailyadvisor.model.entity.UserProfile;
import pl.codepride.dailyadvisor.userservice.model.request.NewUserRequest;
//import pl.codepride.dailyadvisor.model.request.UserProfileRequest;
//import pl.codepride.dailyadvisor.model.response.UserProfileResponse;
//import pl.codepride.dailyadvisor.repository.UserProfileRepository;
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

    private static final String ROLE_COACH = "COACH";

    private static final String ROLE_USER = "USER";

    @Autowired
    @Qualifier("userRepository")
    private UserRepository repository;

//    @Autowired
//    @Qualifier("userProfileRepository")
//    private UserProfileRepository userProfileRepository;

//    @Autowired
//    public VerificationTokenService verificationTokenService;

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
        registerUser(newUserRequest, ROLE_USER);
    }

    @Override
    public void registerCoach(NewUserRequest newUserRequest) {
        registerUser(newUserRequest, ROLE_COACH);
    }

    @Override
    public void registerUser(NewUserRequest newUserRequest, String role) {
        User user = new User(newUserRequest);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        String userRole = Role.valueOf(role).toString();
        user.setRole(userRole);
        repository.save(user);

//        UserProfile userProfile = new UserProfile(userId, newUserRequest);
//        userProfileRepository.save(userProfile);
    }

//    @Override
//    public UserProfileResponse createUserProfileResponseByUser(User userId) {
//        UserProfile userProfile = userProfileRepository.findByUser(userId);
//        return new UserProfileResponse(userId, userProfile);
//    }

//    @Override
//    public void updateUserProfile(UserProfileRequest userProfileRequest, UUID userId) {
//        userProfileRepository.updateUserProfile(userId, userProfileRequest.getCity(), userProfileRequest.getAbout(), userProfileRequest.getName(), userProfileRequest.getLastName());
//    }

//    @Override
//    public void upgradeUserToCoach(User userId) {
//        if (!userId.getRole().equals(Role.COACH.toString())) {
//            userId.set.add(coachRole);
//            repository.save(userId);
//        }
//    }

//    @Override
//    public List<UserProfile> findByUsers(List<User> users) {
//        Set<User> usersSer = new HashSet<>(users);
//        return new ArrayList<>(userProfileRepository.findByUserIn(usersSer));
//    }

//    @Override
//    public List<UserProfile> findByCity(String city) {
//        return userProfileRepository.findByCity(city);
//    }

    @Override
    public User enableUser(User user) throws DataRepositoryException {
        user.setEnabled(true);
        update(user);
        return user;
    }

//    @Override
//    public boolean confirmRegistration(String token) throws DataRepositoryException {
//        User userId = verificationTokenService.confirmToken(token);
//        enableUser(userId);
//        return true;
//    }

    @Override
    public User registerOauth2User(String email) {
        User user = new User();
        user.setEmail(email);
        user.setEnabled(true);
        user.setActive(true);
        user.setRole(Role.USER.toString());
        return repository.save(user);
    }

}
