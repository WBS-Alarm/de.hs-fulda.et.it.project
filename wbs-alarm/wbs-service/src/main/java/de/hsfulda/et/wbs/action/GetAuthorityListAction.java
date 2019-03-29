package de.hsfulda.et.wbs.action;

import de.hsfulda.et.wbs.core.data.AuthorityData;

import java.util.List;

public interface GetAuthorityListAction {

    List<AuthorityData> perform();
}
