package de.hsfulda.et.wbs.action.groesse;

import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.GroesseData;

public interface GetGroesseAction {

    GroesseData perform(WbsUser user, Long id);
}
