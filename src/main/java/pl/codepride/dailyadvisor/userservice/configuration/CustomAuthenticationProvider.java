package pl.codepride.dailyadvisor.userservice.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.codepride.dailyadvisor.userservice.model.Role;
import pl.codepride.dailyadvisor.userservice.model.entity.User;
import pl.codepride.dailyadvisor.userservice.repository.UserRepository;

import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;


    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userRepository.findByEmail(name);
        if (user != null) {
            String passwordEncoded = bCryptPasswordEncoder.encode(password);
            if (bCryptPasswordEncoder.matches(password, passwordEncoded)) {

                List<GrantedAuthority> grantedAuths =
                        AuthorityUtils.commaSeparatedStringToAuthorityList(Role.USER.toString());
                org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(name, password, grantedAuths);
                return new UsernamePasswordAuthenticationToken(userDetails, password, grantedAuths);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
                UsernamePasswordAuthenticationToken.class);
    }
}