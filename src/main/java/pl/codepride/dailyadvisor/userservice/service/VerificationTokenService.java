package pl.codepride.dailyadvisor.userservice.service;

import pl.codepride.dailyadvisor.userservice.model.entity.MailVerificationToken;
import pl.codepride.dailyadvisor.userservice.service.Exceptions.EntityNotFoundException;

import java.util.UUID;

public interface VerificationTokenService extends IService<MailVerificationToken, UUID> {
    UUID confirmToken(String token) throws EntityNotFoundException;
}
