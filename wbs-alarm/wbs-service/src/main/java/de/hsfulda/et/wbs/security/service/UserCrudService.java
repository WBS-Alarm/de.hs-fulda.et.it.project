package de.hsfulda.et.wbs.security.service;

import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.BenutzerCreateDto;
import de.hsfulda.et.wbs.core.data.BenutzerData;

import java.util.Optional;

/**
 * User security operations like login and logout, and CRUD operations on {@link WbsUser}.
 *
 * @author jerome
 */
public interface UserCrudService {

    BenutzerData register(BenutzerCreateDto user);

    Optional<WbsUser> findByUsername(String username);
}