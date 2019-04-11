package de.hsfulda.et.wbs.core.data;

import java.util.List;

public interface BenutzerGrantedAuthData extends BenutzerData {
    List<GrantedAuthorityData> getGrantedAuthorities();
}
