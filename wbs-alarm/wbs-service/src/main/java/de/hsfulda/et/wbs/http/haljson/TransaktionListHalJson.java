package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.TransaktionData;
import de.hsfulda.et.wbs.http.resource.TransaktionListResource;
import de.hsfulda.et.wbs.util.UriUtil;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class TransaktionListHalJson extends HalJsonResource {

    public static final String QUERY_OPTIONS = "?page=%s&size=%s";

    public TransaktionListHalJson(WbsUser user, Page<TransaktionData> transaktionen, Long traegerId, int page,
            int size) {
        String selfUrl = UriUtil.build(TransaktionListResource.PATH, traegerId);
        addLinks(selfUrl, page, size, transaktionen.getTotalPages());

        addProperty("page", page);
        addProperty("size", size);
        addProperty("totalPages", transaktionen.getTotalPages());
        addProperty("totalElements", transaktionen.getTotalElements());

        List<HalJsonResource> resources = transaktionen.stream()
                .map(t -> new TransaktionHalJson(user, t))
                .collect(Collectors.toList());

        addEmbeddedResources("elemente", resources);
    }

    private void addLinks(String selfUrl, int page, int size, int totalPages) {
        addLink(Link.self(selfUrl + String.format(QUERY_OPTIONS, page, size)));

        addNavigationLink(size, selfUrl, "first", 0);
        addNavigationLink(size, selfUrl, "last", totalPages - 1);
        if (page > 0) {
            addNavigationLink(size, selfUrl, "prev", page - 1);
        }

        if ((totalPages - 1) < page) {
            addNavigationLink(size, selfUrl, "next", page + 1);
        }
    }

    private void addNavigationLink(int size, String selfUrl, String prev, int i) {
        addLink(Link.create(prev, selfUrl + String.format(QUERY_OPTIONS, i, size)));
    }
}
