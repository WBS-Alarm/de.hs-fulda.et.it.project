package de.hsfulda.et.wbs.http.haljson.report;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.data.KategorieViewData;
import de.hsfulda.et.wbs.util.UriUtil;

import java.util.List;
import java.util.stream.Collectors;

import static de.hsfulda.et.wbs.Relations.REL_REP_KATEG;
import static de.hsfulda.et.wbs.core.Link.self;

public class KategorieReportHalJson extends HalJsonResource {

    public KategorieReportHalJson(List<KategorieViewData> data, Long traegerId) {
        String selfLink = UriUtil.build(REL_REP_KATEG, traegerId);
        addLink(self(selfLink));

        addEmbeddedResources("elements", data.stream()
                .map(KategorieRecordHalJson::new)
                .collect(Collectors.toList()));
    }
}
