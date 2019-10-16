package de.hsfulda.et.wbs.action.kategorie;

import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.KategorieData;
import de.hsfulda.et.wbs.core.dto.KategorieDto;

public interface UpdateKategorieAction {

    KategorieData perform(WbsUser user, Long id, KategorieDto kategorie);
}
