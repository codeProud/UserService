package pl.codepride.dailyadvisor.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import pl.codepride.dailyadvisor.userservice.repository.UserRepository;

@RestController
public class PopulateController {

    @Autowired
    private UserRepository userRepository;

}
