package pl.codepride.dailyadvisor.userservice.repository;


import pl.codepride.dailyadvisor.userservice.model.entity.VerificationToken;


public interface VerificationTokenRepository extends SimplyRepository<VerificationToken> {

    VerificationToken findOneByToken(String token);
}
