package pl.codepride.dailyadvisor.userservice.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public class ProxyOauth2ClientcontextFilter extends OAuth2ClientContextFilter {

    @Value("${server.prefix}")
    private String serverPrefix;

    protected String calculateCurrentUri(HttpServletRequest request)
            throws UnsupportedEncodingException {

        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder
                .fromRequest(request);
        // Now work around SPR-10172...
        String queryString = request.getQueryString();
        builder.replacePath("/" + serverPrefix + request.getRequestURI());
        boolean legalSpaces = queryString != null && queryString.contains("+");
        if (legalSpaces) {
            builder.replaceQuery(queryString.replace("+", "%20"));
        }
        UriComponents uri = null;
        try {
            uri = builder.replaceQueryParam("code").build(true);
        } catch (IllegalArgumentException ex) {
            // ignore failures to parse the url (including query string). does't
            // make sense for redirection purposes anyway.
            return null;
        }
        String query = uri.getQuery();
        if (legalSpaces) {
            query = query.replace("%20", "+");
        }
        return ServletUriComponentsBuilder.fromUri(uri.toUri())
                .replaceQuery(query).build().toString();
    }
}
