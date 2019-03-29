package de.hsfulda.et.wbs.action.zielort;

import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.ZielortData;
import de.hsfulda.et.wbs.core.data.ZielortDto;

public interface UpdateZielortAction {

    ZielortData perform(WbsUser user, Long id, ZielortDto zielort);
}
