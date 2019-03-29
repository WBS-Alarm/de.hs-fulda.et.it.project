package de.hsfulda.et.wbs.action.bestand;

import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.BestandData;

public interface GetBestandAction {

    BestandData perform(WbsUser user, Long id);
}
