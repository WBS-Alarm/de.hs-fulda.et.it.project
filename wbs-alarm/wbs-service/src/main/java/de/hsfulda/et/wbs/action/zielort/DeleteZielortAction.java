package de.hsfulda.et.wbs.action.zielort;

import de.hsfulda.et.wbs.core.WbsUser;

public interface DeleteZielortAction {

    void perform(WbsUser user, Long id);
}
