package de.hsfulda.et.wbs.security.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.entity.Benutzer;
import de.hsfulda.et.wbs.security.entity.Authority;
import de.hsfulda.et.wbs.security.resource.GrantAuthorityResource;
import de.hsfulda.et.wbs.util.UriUtil;

public class AuthorityHalJson extends HalJsonResource {

    public AuthorityHalJson(final Authority authority, final Benutzer benutzer) {
        addLink(Link.create("delete",
                UriUtil.build(GrantAuthorityResource.PATH, authority.getId(), benutzer.getId())));

        addProperties(authority);
    }

    public AuthorityHalJson(final Authority authority) {
        addProperties(authority);
    }

    private void addProperties(Authority authority) {
        addLink(Link.create("grant","/authority/" + authority.getId() + "/grant/{benutzerId}"));

        addProperty("id", authority.getId());
        addProperty("code", authority.getCode());
        addProperty("bezeichnung", authority.getBezeichnung());
    }
}
