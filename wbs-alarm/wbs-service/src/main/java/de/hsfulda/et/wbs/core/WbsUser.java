package de.hsfulda.et.wbs.core;

import de.hsfulda.et.wbs.core.data.BenutzerData;
import org.springframework.security.core.userdetails.UserDetails;

public interface WbsUser extends UserDetails {

    Long getId();

    BenutzerData getBenutzer();

    boolean isAdmin();

    boolean isTraegerManager();
}
