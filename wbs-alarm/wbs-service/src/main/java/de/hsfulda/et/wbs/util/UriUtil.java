package de.hsfulda.et.wbs.util;

import org.springframework.web.util.UriTemplate;

public abstract class UriUtil {

    private UriUtil() {
    }

    public static String build(String uri, Object... params) {
        return new UriTemplate(uri).expand(params)
                .toString();
    }
}
