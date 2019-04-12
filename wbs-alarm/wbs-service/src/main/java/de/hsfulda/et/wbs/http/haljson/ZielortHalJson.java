package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.ZielortData;
import de.hsfulda.et.wbs.http.resource.ZielortListResource;
import de.hsfulda.et.wbs.util.UriUtil;

import static de.hsfulda.et.wbs.Application.CONTEXT_ROOT;

public class ZielortHalJson extends HalJsonResource {

    public ZielortHalJson(WbsUser user, ZielortData zielort) {
        this(user, zielort, true);
    }

    public ZielortHalJson(WbsUser user, ZielortData zielort, boolean embedded) {
        addZielortProperties(user, zielort);

        if (embedded) {
            addEmbeddedResource("traeger", new TraegerHalJson(user, zielort.getTraeger(), false));
        }
    }

    private void addZielortProperties(WbsUser user, ZielortData zielort) {
        String zielortResource = UriUtil.build(CONTEXT_ROOT + "/zielort/{id}", zielort.getId());

        addLink(Link.self(zielortResource));
        if (user.isTraegerManager()) {
            addLink(Link.create("add", ZielortListResource.PATH));
            addLink(Link.create("delete", zielortResource));
            addLink(Link.create("update", zielortResource));
            if(!zielort.isErfasst()) {
                addLink(Link.create("lock", zielortResource + "/lock"));
            }
        }

        addProperty("id", zielort.getId());
        addProperty("name", zielort.getName());
        addProperty("erfasst", zielort.isErfasst());
        addProperty("generated", zielort.isAuto());
    }
}
