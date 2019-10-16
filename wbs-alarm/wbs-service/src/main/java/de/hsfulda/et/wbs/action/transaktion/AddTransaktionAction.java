package de.hsfulda.et.wbs.action.transaktion;

import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.TransaktionData;
import de.hsfulda.et.wbs.core.dto.TransaktionDto;

public interface AddTransaktionAction {

    TransaktionData perform(WbsUser user, TransaktionDto dto);
}
