package de.hsfulda.et.wbs.action;

import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.BenutzerData;
import de.hsfulda.et.wbs.core.data.BenutzerDto;

public interface UpdateBenutzerAction {

    BenutzerData perform(WbsUser user, Long id, BenutzerDto benutzer);
}
