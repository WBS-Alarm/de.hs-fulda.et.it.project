package de.hsfulda.et.wbs.resource.core;

import static java.util.Objects.requireNonNull;

/**
 * Link zu einer Resource die einen Namen besitzt.
 */
public class NamedLink extends Link {

    private final String name;

    private NamedLink(final String rel, final String href, final String name) {
        super(rel, href);
        requireNonNull(name);
        this.name = name;
    }

    /**
     * Liefert den Namen des Links zurück.
     *
     * @return Liefert den Namen des Links zurück.
     */
    public String getName() {
        return name;
    }

    /**
     * Erstellt einen Link mit den gegebenen Parametern. Link erhält hier ein zusätzliches Attribut {@code name}.
     *
     * @param rel Relation die der Link erhalten soll.
     * @param href Href den der Link erhalten soll.
     * @param name Name des Links
     * @return der erstellte Link.
     */
    public static NamedLink create(final String rel, final String href, final String name) {
        return new NamedLink(rel, href, name);
    }

    /**
     * Erstellt einen Link mit den gegebenen Parametern. Link ist ein {@link NamedLink} genau dann, wenn {@code name}
     * nicht <code>null</code> ist.
     *
     * @param rel Relation die der Link erhalten soll.
     * @param href Href den der Link erhalten soll.
     * @param name Name des Links oder <code>null</code>.
     * @return Der erstellte Link.
     */
    public static Link createPossiblyWithoutName(final String rel, final String href, final String name) {
        if (name == null) {
            return Link.create(rel, href);
        }
        return create(rel, href, name);
    }

    /**
     * Erstellt einen Link mit der IANA-Relation "self" mit den gegebenen Parametern. Link erhält hier ein zusätzliches
     * Attribut {@code name}.
     *
     * @param href Href den der Link erhalten soll.
     * @param name Name des Links
     * @return der erstellte Link.
     */
    public static NamedLink self(final String href, final String name) {
        return new NamedLink(Link.REL_SELF, href, name);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + name.hashCode();
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        // Class Vergleich erfolgt in Link.equals(Object)
        NamedLink other = (NamedLink) obj;
        return name.equals(other.name);
    }

}
