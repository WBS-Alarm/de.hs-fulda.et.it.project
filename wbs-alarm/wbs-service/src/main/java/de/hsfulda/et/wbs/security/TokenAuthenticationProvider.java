package de.hsfulda.et.wbs.security;

import de.hsfulda.et.wbs.security.service.UserAuthenticationService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
final class TokenAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private UserAuthenticationService auth;

    public TokenAuthenticationProvider(UserAuthenticationService auth) {
        this.auth = auth;
    }

    @Override
    protected void additionalAuthenticationChecks(final UserDetails d, final UsernamePasswordAuthenticationToken auth) {
        // Nothing to do
    }

    @Override
    protected UserDetails retrieveUser(final String username,
            final UsernamePasswordAuthenticationToken authentication) {
        final Object token = authentication.getCredentials();
        return Optional.ofNullable(token)
                .map(String::valueOf)
                .flatMap(auth::findByToken)
                .orElseThrow(
                        () -> new UsernameNotFoundException("Cannot find user with authentication token=" + token));
    }
}