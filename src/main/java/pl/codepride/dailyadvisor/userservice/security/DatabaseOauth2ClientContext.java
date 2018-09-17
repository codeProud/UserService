package pl.codepride.dailyadvisor.userservice.security;

import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * Custom decorator to default Oauth2ClientContext with database state repository.
 */
public class DatabaseOauth2ClientContext implements OAuth2ClientContext {

    private String preservedState;

    /**
     * Oauth2 client context.
     */
    private OAuth2ClientContext oAuth2ClientContext;

    /**
     * The constructor.
     * @param oAuth2ClientContext Decorated context.
     */
    public DatabaseOauth2ClientContext(OAuth2ClientContext oAuth2ClientContext) {
        this.oAuth2ClientContext = oAuth2ClientContext;
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
        this.preservedState = (String) preservedState;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object removePreservedState(String stateKey) {
        return this.preservedState;
    }
}
