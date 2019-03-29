package de.hsfulda.et.wbs.action.kategorie;

import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.KategorieData;

import java.util.List;

public interface GetKategorieListAction {

    List<KategorieData> perform(WbsUser user, Long traegerId);
}
