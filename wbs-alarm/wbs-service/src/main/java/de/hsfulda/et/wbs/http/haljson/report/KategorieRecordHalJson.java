package de.hsfulda.et.wbs.http.haljson.report;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.data.KategorieViewData;

public class KategorieRecordHalJson extends HalJsonResource {

    public KategorieRecordHalJson(KategorieViewData data) {
        addProperty("traeger", data.getTraeger());
        addProperty("kategorie", data.getKategorie());
        addProperty("anzahl", data.getAnzahl() == null ? 0L : data.getAnzahl());
    }
}
