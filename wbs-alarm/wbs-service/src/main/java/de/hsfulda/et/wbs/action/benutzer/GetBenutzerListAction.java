package de.hsfulda.et.wbs.action.benutzer;

import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.BenutzerGrantedAuthData;

import java.util.List;

public interface GetBenutzerListAction {

    List<BenutzerGrantedAuthData> perform(WbsUser user, Long traegerId);
}
