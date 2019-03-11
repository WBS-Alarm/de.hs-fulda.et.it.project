package de.hsfulda.et.wbs.security.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.security.entity.Authority;

public class AuthorityHalJson extends HalJsonResource {

    public AuthorityHalJson(final Authority authority) {
        addProperty("id", authority.getId());
        addProperty("code", authority.getCode());
        addProperty("bezeichnung", authority.getBezeichnung());
    }
}
