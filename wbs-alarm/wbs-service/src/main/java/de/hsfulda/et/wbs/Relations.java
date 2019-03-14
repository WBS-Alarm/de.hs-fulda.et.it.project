package de.hsfulda.et.wbs;

import de.hsfulda.et.wbs.http.resource.*;
import de.hsfulda.et.wbs.security.resource.*;

public enum Relations {

    REL_USER_LOGOUT("logout", UserLogoutResource.PATH),
    REL_USER_CURRENT("current", CurrentUserResource.PATH),
    REL_USER_REGISTER("registerUser", UserRegisterResource.PATH),
    REL_AUTHORITIES("authorities", AuthorityResource.PATH),
    REL_GRANT_AUTHORITY("grantAuthority", GrantAuthorityResource.PATH),
    REL_USER_LOGIN("userLogin", LoginResource.PATH),
    REL_BENUTZER("benutzer", BenutzerResource.PATH),
    REL_ZIELORT_LIST("zielortList", ZielortListResource.PATH),
    REL_ZIELORT("zielort", ZielortResource.PATH),
    REL_TRAEGER_LIST("traegerList", TraegerListResource.PATH),
    REL_TRAEGER("traeger", TraegerResource.PATH);

    private final String rel;
    private final String href;

    Relations(String rel, String href) {
        this.rel = rel;
        this.href = href.substring(1);
    }

    public String getRel() {
        return rel;
    }

    public String getHref() {
        return href;
    }
}
