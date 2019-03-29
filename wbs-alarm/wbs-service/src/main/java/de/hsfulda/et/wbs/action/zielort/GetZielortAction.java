package de.hsfulda.et.wbs.action.zielort;

import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.ZielortData;

public interface GetZielortAction {

    ZielortData perform(WbsUser user, Long id);
}
