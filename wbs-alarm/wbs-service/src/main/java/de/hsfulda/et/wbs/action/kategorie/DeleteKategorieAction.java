package de.hsfulda.et.wbs.action.kategorie;

import de.hsfulda.et.wbs.core.WbsUser;

public interface DeleteKategorieAction {

    void perform(WbsUser user, Long id);
}
