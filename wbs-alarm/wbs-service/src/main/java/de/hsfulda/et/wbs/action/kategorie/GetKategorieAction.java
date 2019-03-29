package de.hsfulda.et.wbs.action.kategorie;

import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.KategorieData;

public interface GetKategorieAction {

    KategorieData perform(WbsUser user, Long id);
}
