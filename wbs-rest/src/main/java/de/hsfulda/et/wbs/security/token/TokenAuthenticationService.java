package de.hsfulda.et.wbs.security.token;

import com.google.common.collect.ImmutableMap;
import de.hsfulda.et.wbs.security.Password;
import de.hsfulda.et.wbs.security.User;
import de.hsfulda.et.wbs.service.UserAuthenticationService;
import de.hsfulda.et.wbs.service.UserCrudService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class TokenAuthenticationService implements UserAuthenticationService {

    @NonNull
    TokenService tokens;

    @NonNull
    UserCrudService users;

    @Override
    public Optional<String> login(final String username, final String password) {
        Optional<User> user = users.findByUsername(username);

        Optional<String> createdToken = user
            .filter(u -> Password.checkPassword(password, u.getPassword()))
            .map(u -> tokens.expiring(ImmutableMap.of("username", username)));

        if (user.isPresent() && createdToken.isPresent()) {
            users.save(user.get(), createdToken.get());
        }

        return createdToken;
    }

    @Override
    public Optional<User> findByToken(final String token) {
        Optional<User> user = Optional
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
    public void logout(final User user) {
        users.deleteToken(user);
    }
}