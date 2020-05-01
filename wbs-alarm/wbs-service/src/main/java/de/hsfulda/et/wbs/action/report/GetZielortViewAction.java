package de.hsfulda.et.wbs.action.report;

import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.ZielortViewData;

import java.util.List;

public interface GetZielortViewAction {

    List<ZielortViewData> perform(WbsUser user, Long traegerId);
}
