package de.hsfulda.et.wbs.security.service;

import de.hsfulda.et.wbs.core.WbsUser;

import java.util.Optional;

/**
 * User security operations like login and logout, and CRUD operations on {@link WbsUser}.
 *
 * @author jerome
 */
public interface UserCrudService {

    WbsUser register(WbsUser user);

    WbsUser save(WbsUser user, String token);

    Optional<String> getToken(WbsUser user);

    void deleteToken(WbsUser user);

    Optional<WbsUser> findByUsername(String username);
}