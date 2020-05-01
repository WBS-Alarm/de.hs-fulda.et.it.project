package de.hsfulda.et.wbs.util;

import de.hsfulda.et.wbs.Relations;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

import java.util.Collections;

public class HeaderUtil {

    private HeaderUtil() {
        // noop
    }

    public static MultiValueMap<String, String> locationHeader(Relations rel, Object... params) {
        MultiValueMap<String, String> header = new HttpHeaders();
        header.put("Location", Collections.singletonList(UriUtil.build(rel, params)));
        return header;
    }
}
