package de.hsfulda.et.wbs.action.bestand;

import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.BestandData;

import java.util.List;

public interface GetBestandListAction {

    List<BestandData> perform(WbsUser user, Long zielortId);
}
