package de.hsfulda.et.wbs.action.groesse;

import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.GroesseData;
import de.hsfulda.et.wbs.core.data.GroesseDto;

public interface CreateGroesseAction {

    GroesseData peform(WbsUser user, Long kategorieId, GroesseDto groesse);
}
