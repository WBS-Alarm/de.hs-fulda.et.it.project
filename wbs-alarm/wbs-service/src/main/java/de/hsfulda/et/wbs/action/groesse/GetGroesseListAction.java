package de.hsfulda.et.wbs.action.groesse;

import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.GroesseData;

import java.util.List;

public interface GetGroesseListAction {

    List<GroesseData> perform(WbsUser user, Long kategorieId);
}
