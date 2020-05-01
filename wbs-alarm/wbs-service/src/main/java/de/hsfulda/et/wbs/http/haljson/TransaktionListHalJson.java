package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.TransaktionData;
import de.hsfulda.et.wbs.util.UriUtil;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

import static de.hsfulda.et.wbs.Relations.REL_TRANSAKTION_LIST;

public class TransaktionListHalJson extends HalJsonResource {

    public static final String QUERY_OPTIONS = "?page=%s&size=%s";

    public TransaktionListHalJson(WbsUser user, Page<TransaktionData> transaktionen, Long traegerId, int page,
            int size) {
        String selfUrl = UriUtil.build(REL_TRANSAKTION_LIST, traegerId);
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

        addNavigationLink(selfUrl, "first", size, 0);
        addNavigationLink(selfUrl, "last", size, totalPages - 1);
        if (page > 0) {
            addNavigationLink(selfUrl, "prev", size, page - 1);
        }

        if ((totalPages - 1) < page) {
            addNavigationLink(selfUrl, "next", size, page + 1);
        }
    }

    private void addNavigationLink(String selfUrl, String rel, int size, int i) {
        addLink(Link.create(rel, selfUrl + String.format(QUERY_OPTIONS, i, size)));
    }
}
