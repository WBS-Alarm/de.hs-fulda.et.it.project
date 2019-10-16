package de.hsfulda.et.wbs.action.transaktion;

import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.TransaktionData;
import de.hsfulda.et.wbs.core.dto.PositionDto;

import java.util.List;

public interface AddEinkaufAction {

    TransaktionData perform(WbsUser user, Long tragerId, List<PositionDto> dto);
}
