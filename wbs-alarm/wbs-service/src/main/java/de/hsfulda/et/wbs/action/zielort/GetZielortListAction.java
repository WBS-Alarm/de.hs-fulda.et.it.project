package de.hsfulda.et.wbs.action.zielort;

import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.ZielortData;

import java.util.List;

public interface GetZielortListAction {

    List<ZielortData> perform(WbsUser user, Long traegerId);
}
