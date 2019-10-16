package de.hsfulda.et.wbs.action.transaktion;

import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.TransaktionData;

public interface GetTransaktionAction {

    TransaktionData perform(WbsUser user, Long id);
}
