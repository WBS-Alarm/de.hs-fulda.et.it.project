package de.hsfulda.et.wbs.http.haljson.report;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.data.BestandViewData;
import de.hsfulda.et.wbs.util.UriUtil;

import java.util.List;
import java.util.stream.Collectors;

import static de.hsfulda.et.wbs.Relations.REL_REP_BESTR;
import static de.hsfulda.et.wbs.core.Link.self;

public class BestandReportHalJson extends HalJsonResource {

    public BestandReportHalJson(List<BestandViewData> data, Long traegerId) {
        String selfLink = UriUtil.build(REL_REP_BESTR, traegerId);
        addLink(self(selfLink));

        addEmbeddedResources("elements", data.stream()
                .map(BestandRecordHalJson::new)
                .collect(Collectors.toList()));
    }
}
