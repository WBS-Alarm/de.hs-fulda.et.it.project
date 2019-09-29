package de.hsfulda.et.wbs.action.groesse;

import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.GroesseData;
import de.hsfulda.et.wbs.core.dto.GroesseDto;

public interface CreateGroesseAction {

    GroesseData perform(WbsUser user, Long kategorieId, GroesseDto groesse);
}
