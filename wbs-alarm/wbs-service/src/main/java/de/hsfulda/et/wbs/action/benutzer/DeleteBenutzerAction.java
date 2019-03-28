package de.hsfulda.et.wbs.action.benutzer;

import de.hsfulda.et.wbs.core.WbsUser;

public interface DeleteBenutzerAction {

    void perform(WbsUser user, Long id);
}
