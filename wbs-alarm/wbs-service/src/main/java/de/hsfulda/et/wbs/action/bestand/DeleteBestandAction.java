package de.hsfulda.et.wbs.action.bestand;

import de.hsfulda.et.wbs.core.WbsUser;

public interface DeleteBestandAction {

    void perform(WbsUser user, Long id);
}
