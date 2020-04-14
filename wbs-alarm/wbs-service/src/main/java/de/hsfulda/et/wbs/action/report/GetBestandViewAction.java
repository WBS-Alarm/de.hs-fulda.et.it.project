package de.hsfulda.et.wbs.action.report;

import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.BestandViewData;

import java.util.List;

public interface GetBestandViewAction {

    List<BestandViewData> perform(WbsUser user, Long traegerId);
}
