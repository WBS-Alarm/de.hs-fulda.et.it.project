package de.hsfulda.et.wbs.action;

import de.hsfulda.et.wbs.core.data.GrantedAuthorityData;

import java.util.List;

public interface GetGrantedAuthorityListAction {

    List<GrantedAuthorityData> perform(Long benutzerId);
}
