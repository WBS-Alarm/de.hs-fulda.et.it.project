package de.hsfulda.et.wbs.security.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.AuthorityData;
import de.hsfulda.et.wbs.core.data.BenutzerData;
import de.hsfulda.et.wbs.security.resource.GrantAuthorityResource;
import de.hsfulda.et.wbs.util.UriUtil;

import static de.hsfulda.et.wbs.Application.CONTEXT_ROOT;

public class AuthorityHalJson extends HalJsonResource {

    public AuthorityHalJson(WbsUser user, final AuthorityData authority, final BenutzerData benutzer) {
        addLink(Link.create("delete",
            UriUtil.build(GrantAuthorityResource.PATH, authority.getId(), benutzer.getId())));

        addProperties(user, authority);
    }

    public AuthorityHalJson(WbsUser user, final AuthorityData authority) {
        addProperties(user, authority);
    }

    private void addProperties(WbsUser user, AuthorityData authority) {
        if (user.isTraegerManager()) {
            addLink(Link.create("grant", CONTEXT_ROOT + "/authority/" + authority.getId() + "/grant/{benutzerId}"));
        }

        addProperty("id", authority.getId());
        addProperty("code", authority.getCode());
        addProperty("bezeichnung", authority.getBezeichnung());
    }
}
