package de.hsfulda.et.wbs.action.traeger;

import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.TraegerData;

import java.util.List;

public interface GetTraegerListAction {

    List<TraegerData> perform(WbsUser user);
}
