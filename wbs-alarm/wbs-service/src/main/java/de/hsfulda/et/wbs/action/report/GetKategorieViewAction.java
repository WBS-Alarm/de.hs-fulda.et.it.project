package de.hsfulda.et.wbs.action.report;

import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.KategorieViewData;

import java.util.List;

public interface GetKategorieViewAction {

    List<KategorieViewData> perform(WbsUser user, Long traegerId);
}
