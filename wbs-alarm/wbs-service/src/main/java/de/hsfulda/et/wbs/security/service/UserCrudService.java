package de.hsfulda.et.wbs.security.service;

import de.hsfulda.et.wbs.core.User;

import java.util.Optional;

/**
 * User security operations like login and logout, and CRUD operations on {@link User}.
 *
 * @author jerome
 */
public interface UserCrudService {

    User register(User user);

    User save(User user, String token);

    Optional<String> getToken(User user);

    void deleteToken(User user);

    Optional<User> findByUsername(String username);
}