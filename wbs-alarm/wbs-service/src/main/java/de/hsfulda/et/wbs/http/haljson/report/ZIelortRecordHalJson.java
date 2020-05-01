package de.hsfulda.et.wbs.http.haljson.report;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.data.ZielortViewData;

public class ZIelortRecordHalJson extends HalJsonResource {

    public ZIelortRecordHalJson(ZielortViewData data) {
        addProperty("traeger", data.getTraeger());
        addProperty("zielort", data.getZielort());
        addProperty("kategorie", data.getKategorie());
        addProperty("anzahl", data.getAnzahl());
    }
}
