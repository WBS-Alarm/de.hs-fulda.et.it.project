package de.hsfulda.et.wbs.util;

import de.hsfulda.et.wbs.Relations;
import org.springframework.web.util.UriTemplate;

public abstract class UriUtil {

    private UriUtil() {
    }

    public static String build(Relations rel, Object... params) {
        return new UriTemplate(rel.getSlashedHref()).expand(params)
                .toString();
    }
}
