package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.core.data.ZielortData;
import de.hsfulda.et.wbs.http.resource.ZielortListResource;
import de.hsfulda.et.wbs.util.UriUtil;

import static de.hsfulda.et.wbs.Application.CONTEXT_ROOT;

public class ZielortHalJson extends HalJsonResource {

    public ZielortHalJson(ZielortData zielort) {
        this(zielort, true);
    }

    public ZielortHalJson(ZielortData zielort, boolean embedded) {
        addZielortProperties(zielort);

        if (embedded) {
            addEmbeddedResource("traeger", new TraegerHalJson(zielort.getTraeger(), false));
        }
    }

    private void addZielortProperties(ZielortData zielort) {
        String zielortResource = UriUtil.build(CONTEXT_ROOT + "/zielort/{id}", zielort.getId());

        addLink(Link.self(zielortResource));
        addLink(Link.create("add", ZielortListResource.PATH));
        addLink(Link.create("delete", zielortResource));
        addLink(Link.create("update", zielortResource));

        addProperty("id", zielort.getId());
        addProperty("name", zielort.getName());
    }
}
