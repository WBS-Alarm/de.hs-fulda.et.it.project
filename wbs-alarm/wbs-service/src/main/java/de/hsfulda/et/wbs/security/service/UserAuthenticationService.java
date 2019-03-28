package de.hsfulda.et.wbs.security.service;

import de.hsfulda.et.wbs.core.WbsUser;

import java.util.Optional;

public interface UserAuthenticationService {

    /**
     * Logs in with the given {@code username} and {@code password}.
     *
     * @param username given username
     * @param password given password
     * @return an {@link Optional} of a users token when login succeeds
     */
    Optional<String> login(String username, String password);

    /**
     * Finds a user by its dao-key.
     *
     * @param token user dao key
     * @return Registered User
     */
    Optional<WbsUser> findByToken(String token);

    /**
     * Logs out the given input {@code user}.
     *
     * @param user the user to logout
     */
    void logout(WbsUser user);
}