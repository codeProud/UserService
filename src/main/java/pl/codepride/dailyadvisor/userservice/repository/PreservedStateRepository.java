package pl.codepride.dailyadvisor.userservice.repository;

import org.springframework.data.cassandra.repository.AllowFiltering;
import pl.codepride.dailyadvisor.userservice.model.entity.OAuth2PreservedState;

import java.util.Optional;

public interface PreservedStateRepository extends SimplyRepository<OAuth2PreservedState> {
    @AllowFiltering
    public Optional<OAuth2PreservedState> findByStateKey(String stateKey);
}
