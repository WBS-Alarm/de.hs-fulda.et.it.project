package de.hsfulda.et.wbs.action.kategorie;

import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.KategorieData;
import de.hsfulda.et.wbs.core.dto.KategorieDto;

public interface CreateKategorieAction {

    KategorieData perform(WbsUser user, Long traegerId, KategorieDto kategorie);
}
