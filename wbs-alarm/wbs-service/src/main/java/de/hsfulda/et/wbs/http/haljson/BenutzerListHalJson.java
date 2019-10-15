package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.BenutzerGrantedAuthData;
import de.hsfulda.et.wbs.http.resource.BenutzerListResource;
import de.hsfulda.et.wbs.util.UriUtil;

import java.util.List;
import java.util.stream.Collectors;

public class BenutzerListHalJson extends HalJsonResource {

    public BenutzerListHalJson(WbsUser user, List<BenutzerGrantedAuthData> benutzer, Long traegerId) {
        addLink(Link.self(UriUtil.build(BenutzerListResource.PATH, traegerId)));

        List<HalJsonResource> resources = benutzer.stream()
                .map(t -> BenutzerHalJson.ofGrantedAuthoritiesNoEmbaddables(user, t, t.getGrantedAuthorities()))
                .collect(Collectors.toList());

        addEmbeddedResources("elemente", resources);

    }
}
