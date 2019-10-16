package de.hsfulda.et.wbs.action.zielort;

import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.ZielortData;
import de.hsfulda.et.wbs.core.dto.ZielortDto;

public interface CreateZielortAction {

    ZielortData perform(WbsUser user, Long traegerId, ZielortDto zielort);
}
