package pl.codepride.dailyadvisor.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pl.codepride.dailyadvisor.userservice.security.JWTManager;

@Controller
public class LogoutController {

    @Autowired
    private JWTManager jwtManager;

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity logout() {
        ServletRequestAttributes attributes =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes());
        jwtManager.jwtLogout(attributes.getRequest(),attributes.getResponse());
        return new ResponseEntity(HttpStatus.OK);
    }
}
