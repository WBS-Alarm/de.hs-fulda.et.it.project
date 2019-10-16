package de.hsfulda.et.wbs.action.bestand;

import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.BestandData;
import de.hsfulda.et.wbs.core.dto.BestandCreateDto;

public interface CreateBestandAction {

    BestandData perform(WbsUser user, Long zielortId, BestandCreateDto bestand);
}
