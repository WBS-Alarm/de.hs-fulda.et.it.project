package de.hsfulda.et.wbs.security.token;

import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.security.Password;
import de.hsfulda.et.wbs.security.User;
import de.hsfulda.et.wbs.security.service.UserAuthenticationService;
import de.hsfulda.et.wbs.security.service.UserCrudService;
import de.hsfulda.et.wbs.util.ImmutableMap;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
final class TokenAuthenticationService implements UserAuthenticationService {

    private final TokenService tokens;
    private final UserCrudService users;

    public TokenAuthenticationService(TokenService tokens, UserCrudService users) {
        this.tokens = tokens;
        this.users = users;
    }

    @Override
    public Optional<String> login(final String username, final String password) {
        Optional<WbsUser> user = users.findByUsername(username);

        Optional<String> createdToken = user
                .filter(u -> Password.checkPassword(password, u.getPassword()))
                .map(u -> tokens.expiring(ImmutableMap.of("username", username)));

        if (user.isPresent() && createdToken.isPresent()) {
            users.save(user.get(), createdToken.get());
        }

        return createdToken;
    }

    @Override
    public Optional<WbsUser> findByToken(final String token) {
        Optional<WbsUser> user = Optional
                .of(tokens.verify(token))
                .map(map -> map.get("username"))
                .flatMap(users::findByUsername);

        if (user.isPresent()) {
            Optional<String> loadedToken = users.getToken(user.get());
            if (loadedToken.isPresent() && token.equals(loadedToken.get())) {
                return user;
            }

        }
        throw new UsernameNotFoundException("User not authorized");
    }

    @Override
    public void logout(final WbsUser user) {
        users.deleteToken((User) user);
    }
}