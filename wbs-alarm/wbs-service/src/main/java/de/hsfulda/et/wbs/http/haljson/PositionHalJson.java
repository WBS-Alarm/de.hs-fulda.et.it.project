package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.PositionData;

public class PositionHalJson extends HalJsonResource {

    public PositionHalJson(WbsUser user, PositionData position) {

        addEmbeddedResource("groesse", new GroesseHalJson(user, position.getGroesse()));
    }

    private void addPositionProperties(WbsUser user, PositionData position) {
        addProperty("id", position.getId());
        addProperty("anzahl", position.getAnzahl());
    }
}
