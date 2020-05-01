package de.hsfulda.et.wbs.http.haljson.report;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.data.ZielortViewData;
import de.hsfulda.et.wbs.util.UriUtil;

import java.util.List;
import java.util.stream.Collectors;

import static de.hsfulda.et.wbs.Relations.REL_REP_ZIELORT;
import static de.hsfulda.et.wbs.core.Link.self;

public class ZielortReportHalJson extends HalJsonResource {

    public ZielortReportHalJson(List<ZielortViewData> data, Long traegerId) {
        String selfLink = UriUtil.build(REL_REP_ZIELORT, traegerId);
        addLink(self(selfLink));

        addEmbeddedResources("elements", data.stream()
                .map(ZIelortRecordHalJson::new)
                .collect(Collectors.toList()));
    }
}
