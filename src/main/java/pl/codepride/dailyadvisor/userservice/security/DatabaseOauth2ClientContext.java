package pl.codepride.dailyadvisor.userservice.security;

import com.datastax.driver.core.utils.UUIDs;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import pl.codepride.dailyadvisor.userservice.model.entity.OAuth2PreservedState;
import pl.codepride.dailyadvisor.userservice.repository.PreservedStateRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Custom decorator to default Oauth2ClientContext with database state repository.
 */
public class DatabaseOauth2ClientContext implements OAuth2ClientContext {

    private PreservedStateRepository preservedStateRepository;

    /**
     * Oauth2 client context.
     */
    private OAuth2ClientContext oAuth2ClientContext;

    /**
     * The constructor.
     * @param oAuth2ClientContext Decorated context.
     */
    public DatabaseOauth2ClientContext(OAuth2ClientContext oAuth2ClientContext, PreservedStateRepository preservedStateRepository) {
        this.oAuth2ClientContext = oAuth2ClientContext;
        this.preservedStateRepository = preservedStateRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OAuth2AccessToken getAccessToken() {
        return oAuth2ClientContext.getAccessToken();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAccessToken(OAuth2AccessToken accessToken) {
        oAuth2ClientContext.setAccessToken(accessToken);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccessTokenRequest getAccessTokenRequest() {
        return oAuth2ClientContext.getAccessTokenRequest();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPreservedState(String stateKey, Object preservedState) {
        OAuth2PreservedState oAuth2PreservedState = new OAuth2PreservedState();
        oAuth2PreservedState.setId(UUIDs.timeBased());
        oAuth2PreservedState.setPreservedState((String) preservedState);
        oAuth2PreservedState.setStateKey(stateKey);
        preservedStateRepository.insert(oAuth2PreservedState);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object removePreservedState(String stateKey) {
        Optional<OAuth2PreservedState> oAuth2PreservedState = this.preservedStateRepository.findByStateKey(stateKey);
        oAuth2PreservedState.ifPresent(state -> {
            preservedStateRepository.delete(state);
        });
        return oAuth2PreservedState
                .flatMap(state -> Optional.ofNullable(state.getPreservedState()))
                .orElseGet(null);
    }
}
