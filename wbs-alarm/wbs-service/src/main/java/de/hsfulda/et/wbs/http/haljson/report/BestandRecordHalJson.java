package de.hsfulda.et.wbs.http.haljson.report;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.data.BestandViewData;

public class BestandRecordHalJson extends HalJsonResource {

    public BestandRecordHalJson(BestandViewData data) {
        addProperty("traeger", data.getTraeger());
        addProperty("zielort", data.getZielort());
        addProperty("kategorie", data.getKategorie());
        addProperty("groesse", data.getGroesse());
        addProperty("anzahl", data.getAnzahl());
    }
}
