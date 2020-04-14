package de.hsfulda.et.wbs.http.haljson.report;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.data.BestandViewData;
import de.hsfulda.et.wbs.http.resource.report.BestandReportResource;
import de.hsfulda.et.wbs.util.UriUtil;

import java.util.List;
import java.util.stream.Collectors;

import static de.hsfulda.et.wbs.core.Link.self;

public class BestandReportHalJson extends HalJsonResource {

    public BestandReportHalJson(List<BestandViewData> data, Long traegerId) {
        String selfLink = UriUtil.build(BestandReportResource.PATH, traegerId);
        addLink(self(selfLink));

        addEmbeddedResources("elements", data.stream()
                .map(BestandRecordHalJson::new)
                .collect(Collectors.toList()));
    }
}
