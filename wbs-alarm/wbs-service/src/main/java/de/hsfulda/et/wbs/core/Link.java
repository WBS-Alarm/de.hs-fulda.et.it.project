package de.hsfulda.et.wbs.core;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

/**
 * Link zu einer Resource.
 */
public class Link {

    private static final String REL_SELF = "self";

    /*
     * Rel wird außerhalb des Links dargestellt.
     */
    @JsonIgnore
    private final String rel;
    private final String href;

    private Link(final String rel, final String href) {
        Objects.requireNonNull(rel, "Rel must not be null");
        Objects.requireNonNull(href, "Href must not be null");

        this.rel = rel;
        this.href = href;
    }

    public static Link create(final String rel, final String href) {
        return new Link(rel, href);
    }

    public static Link self(final String href) {
        return create(REL_SELF, href);
    }

    public String getRel() {
        return rel;
    }

    public String getHref() {
        return href;
    }

    public boolean isTemplated() {
        return isTemplated(this.href);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + href.hashCode();
        result = prime * result + rel.hashCode();
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        // Subklassen verlassen sich darauf, dass dieser Vergleich vorhanden ist!
        if (getClass() != obj.getClass()) {
            return false;
        }
        Link other = (Link) obj;
        if (!href.equals(other.href)) {
            return false;
        }
        return rel.equals(other.rel);
    }

    private static boolean isTemplated(String href) {
        int openingBracket = href.indexOf('{');
        if (openingBracket < 0) {
            return false;
        }
        int closingBracket = href.indexOf('}', openingBracket);
        return closingBracket >= 0;
    }

}
