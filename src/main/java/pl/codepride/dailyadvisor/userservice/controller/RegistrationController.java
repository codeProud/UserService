package pl.codepride.dailyadvisor.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.codepride.dailyadvisor.userservice.event.OnRegistrationCompleteEvent;
import pl.codepride.dailyadvisor.userservice.model.Role;
import pl.codepride.dailyadvisor.userservice.model.entity.User;
import pl.codepride.dailyadvisor.userservice.model.request.NewUserRequest;
import pl.codepride.dailyadvisor.userservice.model.request.RegistrationConfirmRequest;
import pl.codepride.dailyadvisor.userservice.service.Exceptions.DataRepositoryException;
import pl.codepride.dailyadvisor.userservice.service.UserService;

import javax.validation.Valid;

@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity createNewUser(@Valid @RequestBody NewUserRequest newUserRequest) {
        if (userService.findUserByEmail(newUserRequest.getEmail()) == null) {
            if (Role.USER.toString().equals(newUserRequest.getUserType())) {
                registerUser(Role.USER.toString(), newUserRequest);
                return new ResponseEntity(HttpStatus.OK);
            } else if (Role.COACH.toString().equals(newUserRequest.getUserType())) {
                registerUser(Role.COACH.toString(), newUserRequest);
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity(HttpStatus.IM_USED);
        }
    }

    @RequestMapping(value = "/registrationConfirm", method = RequestMethod.POST)
    public ResponseEntity confirmRegistration(@RequestBody RegistrationConfirmRequest registrationConfirmRequest) {
        try {
            userService.confirmRegistration(registrationConfirmRequest.getToken());
        } catch (DataRepositoryException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ResponseEntity hello() {
        return new ResponseEntity(HttpStatus.OK);
    }

    private void registerUser(String role, NewUserRequest newUserRequest){
        if(role.equals(Role.COACH.toString())){
            userService.registerCoach(newUserRequest);
        } else if (role.equals(Role.USER.toString())) {
            userService.registerClient(newUserRequest);
        }

        User user = userService.findUserByEmail(newUserRequest.getEmail());
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user));
    }
}
