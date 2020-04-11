package de.hsfulda.et.wbs.action.transaktion;

import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.TransaktionData;
import org.springframework.data.domain.Page;

public interface GetTransaktionListAction {

    Page<TransaktionData> perform(WbsUser user, Long traegerId, int offset, int size);
}
