package de.hsfulda.et.wbs.action.benutzer;

import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.BenutzerData;

public interface GetBenutzerAction {

    BenutzerData perform(WbsUser user, Long id);
}
