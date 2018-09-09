package pl.codepride.dailyadvisor.userservice.service;

import com.datastax.driver.core.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.codepride.dailyadvisor.userservice.model.entity.VerificationToken;
import pl.codepride.dailyadvisor.userservice.repository.VerificationTokenRepository;
import pl.codepride.dailyadvisor.userservice.service.Exceptions.DataRepositoryException;
import pl.codepride.dailyadvisor.userservice.service.Exceptions.EntityExists;
import pl.codepride.dailyadvisor.userservice.service.Exceptions.EntityNotFoundException;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service("verificationTokenService")
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private static final String VERIFICATION_TOKEN_NOT_FOUND_MESSAGE_CODE = "exception.entityNotFoundException.verificationToken";

    private static final String VERIFICATION_TOKEN_EXISTS_MESSAGE_CODE = "exception.entityNotFoundException.verificationToken";

    @Autowired
    @Qualifier("verificationTokenRepository")
    private VerificationTokenRepository repository;

    @Override
    public VerificationToken create(VerificationToken verificationToken) throws DataRepositoryException {
        if(verificationToken.getId() == null || !repository.findById(verificationToken.getId()).isPresent()) {
            return repository.save(verificationToken);
        } else {
            throw new EntityExists(VERIFICATION_TOKEN_EXISTS_MESSAGE_CODE);
        }
    }

    @Override
    public void delete(UUID id) throws DataRepositoryException {
        if (repository.existsById(id)) {

            repository.deleteById(id);
        } else {
            throw new EntityNotFoundException(VERIFICATION_TOKEN_EXISTS_MESSAGE_CODE);
        }
    }

    @Override
    public List<VerificationToken> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<VerificationToken> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public VerificationToken update(VerificationToken verificationToken) throws DataRepositoryException, NoSuchElementException {
        if(repository.findById(verificationToken.getId()).isPresent()){
            return repository.save(verificationToken);
        } else {
            throw new EntityNotFoundException(VERIFICATION_TOKEN_NOT_FOUND_MESSAGE_CODE);
        }
    }

    @Override
    public UUID confirmToken(String token) throws EntityNotFoundException {
        Optional<VerificationToken> verificationToken = repository.findById(UUID.fromString(token));

        if(verificationToken.isPresent() ){
            repository.delete(verificationToken.get());
            return verificationToken.get().getUserId();
        } else {
            throw new EntityNotFoundException(VERIFICATION_TOKEN_NOT_FOUND_MESSAGE_CODE);
        }
    }
}
