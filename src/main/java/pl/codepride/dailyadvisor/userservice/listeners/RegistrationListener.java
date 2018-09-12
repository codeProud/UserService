package pl.codepride.dailyadvisor.userservice.listeners;


import com.datastax.driver.core.utils.UUIDs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import pl.codepride.dailyadvisor.userservice.event.OnRegistrationCompleteEvent;
import pl.codepride.dailyadvisor.userservice.model.entity.User;
import pl.codepride.dailyadvisor.userservice.model.entity.MailVerificationToken;
import pl.codepride.dailyadvisor.userservice.service.Exceptions.DataRepositoryException;
import pl.codepride.dailyadvisor.userservice.service.VerificationTokenService;
import pl.codepride.dailyadvisor.userservice.service.EmailService;

import java.util.UUID;

@Component
public class RegistrationListener implements
        ApplicationListener<OnRegistrationCompleteEvent> {


    private final static String VERIFICATION_MAIL_SUBJECT = "Mail verification";

    @Autowired
    private EmailService emailService;

    @Value("${frontend.server.port}")
    private String serverPort;

    @Autowired
    private VerificationTokenService verificationTokenService;

    private static final Logger logger = LoggerFactory.getLogger(RegistrationListener.class);

    @Override
    @Async
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        UUID token = UUIDs.timeBased();
        MailVerificationToken mailVerificationToken = new MailVerificationToken(event.getUser().getId(), token);
        try {
            verificationTokenService.create(mailVerificationToken);
        } catch (DataRepositoryException e) {
            logger.error("Registration token exists");
        }
        String text = "http://localhost:" + serverPort + "/registrationConfirm/" + token.toString();
        emailService.sendSimpleMessage(user.getEmail(), VERIFICATION_MAIL_SUBJECT, text);
    }

}