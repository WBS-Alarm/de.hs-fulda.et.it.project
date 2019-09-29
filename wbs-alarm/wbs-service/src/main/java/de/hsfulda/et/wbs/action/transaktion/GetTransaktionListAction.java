package de.hsfulda.et.wbs.action.transaktion;

import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.TransaktionData;

import java.util.List;

public interface GetTransaktionListAction {

    List<TransaktionData> perform(WbsUser user);
}
