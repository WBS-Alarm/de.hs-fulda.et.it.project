package de.hsfulda.et.wbs.action.bestand;

import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.BestandData;
import de.hsfulda.et.wbs.core.data.BestandDto;

public interface UpdateBestandAction {

    BestandData perform(WbsUser user, Long bestandId, BestandDto bestand);
}
