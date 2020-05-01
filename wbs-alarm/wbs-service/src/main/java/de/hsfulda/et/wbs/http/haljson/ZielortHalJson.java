package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.Relations;
import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.TraegerData;
import de.hsfulda.et.wbs.core.data.ZielortData;
import de.hsfulda.et.wbs.util.UriUtil;

import static de.hsfulda.et.wbs.Relations.REL_ZIELORT_LIST;
import static de.hsfulda.et.wbs.Relations.REL_ZIELORT_LOCK;

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
        String zielortResource = UriUtil.build(Relations.REL_ZIELORT, zielort.getId());

        addLink(Link.self(zielortResource));
        if (user.isTraegerManager()) {
            TraegerData traeger = zielort.getTraeger();
            addLink(Link.create("add", UriUtil.build(REL_ZIELORT_LIST, traeger.getId())));
            addLink(Link.create("delete", zielortResource));
            addLink(Link.create("update", zielortResource));
            if (!zielort.isErfasst()) {
                addLink(Link.create("lock", UriUtil.build(REL_ZIELORT_LOCK, zielort.getId())));
            }
        }

        addProperty("id", zielort.getId());
        addProperty("name", zielort.getName());
        addProperty("erfasst", zielort.isErfasst());
        addProperty("generated", zielort.isAuto());
    }
}
