package de.hsfulda.et.wbs.resource.core;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

import static java.util.Objects.requireNonNull;

/**
 * Dynamische Hal-Json-Ressourcen-Implementierung. Dadurch können beliebige Typen hiermit dargestellt werden.
 */
public class HalJsonResource {

    public static final String HAL_JSON = "application/hal+json";

    static final String _EMBEDDED = "_embedded";

    static final String _LINKS = "_links";

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty(_LINKS)
    private final Map<String, List<Link>> links = new HashMap<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty(_EMBEDDED)
    private final Map<String, List<HalJsonResource>> embedded = new HashMap<>();

    private final Map<String, Object> properties = new HashMap<>();

    public HalJsonResource() {
        // No op
    }

    public Map<String, List<Link>> getLinks() {
        return links;
    }

    public Map<String, List<HalJsonResource>> getEmbedded() {
        return embedded;
    }

    /**
     * Fügt einen neuen Link auf die bestehende Liste hinzu.
     *
     * @param link Link der auf die bestehende Liste hinzugefügt werden soll.
     * @throws NullPointerException wenn die {@code link}-Referenz {@code null} war.
     */
    public final void addLink(final Link link) {
        Objects.requireNonNull(link, "The link must not be null");
        List<Link> links = getLinksForRelation(link.getRel());

        links.add(link);
    }

    /**
     * Die Methode sucht Links der Resource heraus, die mit der gesuchten Relation übereinstimmen.
     *
     * @param relation Relation nach der der Link gesucht werden soll.
     * @return Liefert eine Liste von Links zurück, die mit der Relation übereinstimmen ansonsten wird eine leere Liste
     * zurück gegeben.
     */
    public List<Link> getLinks(final String relation) {
        Objects.requireNonNull(relation, "Relation must not be null");

        return Collections.unmodifiableList(getLinksForRelation(relation));
    }

    private List<Link> getLinksForRelation(final String relation) {
        return links.computeIfAbsent(relation, k -> new ArrayList<>());
    }

    /**
     * Die Methode sucht einen Link aus der Linkliste heraus, in der die Relation übereinstimmt.
     * <strong>Vorsicht:</strong> Es wird nur die erste Übereinstimmung gewählt.
     *
     * @param relation Relation nach der der Link gesucht werden soll.
     * @return Liefert den ersten Link zurück, mit der die Relation übereinstimmt ansonsten {@code null}.
     */
    public Link getLink(final String relation) {
        Objects.requireNonNull(relation, "Relation must not be null");

        List<Link> links = getLinksForRelation(relation);

        if (links.size() > 0) {
            return links.get(0);
        }

        return null;
    }

    /**
     * Fügt eine neue Resource auf die bestehende Liste hinzu.
     *
     * @param relation Relation der Resource.
     * @param resource Resource die zu den embedded Resourcen hinzugefügt werden soll.
     */
    public void addEmbeddedResource(final String relation, final HalJsonResource resource) {
        Objects.requireNonNull(resource, "resource must not be null to be embedded in a resource");

        List<HalJsonResource> halJsonResources = ensureEmbeddedForRelationIsNotNull(relation);
        halJsonResources.add(resource);

        embedded.put(relation, halJsonResources);
    }

    private List<HalJsonResource> ensureEmbeddedForRelationIsNotNull(final String relation) {
        List<HalJsonResource> halJsonResources = embedded.get(relation);
        if (halJsonResources == null) {
            halJsonResources = new ArrayList<>();
        }
        return halJsonResources;
    }

    /**
     * Fügt eine neue Liste von Resourcen auf die bestehende Liste hinzu. <em>Achtung:</em> sollten sich schon Resourcen
     * mit dieser Relation in den embedded Resources befinden, so werden diese überschrieben. Ansonsten sollte {@link
     * #addEmbeddedResource(String, HalJsonResource)} verwendet werden.
     *
     * @param relation Relation der Resourcen.
     * @param resources Resourcen die zu den embedded Resourcen hinzugefügt werden sollen.
     */
    public void addEmbeddedResources(final String relation, final List<HalJsonResource> resources) {
        Objects.requireNonNull(resources, "resources must not be null to be embedded in a resource");
        if (resources.contains(null)) {
            throw new NullPointerException("List of resources contains one or more null values");
        }

        embedded.put(relation, resources);
    }

    /**
     * Fügt eine Eigenschaft der HalJsonResource hinzu.
     *
     * @param name Name der Eigenschaft.
     * @param value Wert der Eigenschaft.
     */
    public void addProperty(final String name, final Object value) {
        Objects.requireNonNull(name, "name of the property of the hal+json Resource must not be null");

        properties.put(name, value);
    }

    /**
     * Liefert die Map zurück in der die Eigenschaften der HalJsonResource enthalten sind.
     *
     * @return Liefert die Map zurück in der die Eigenschaften der HalJsonResource enthalten sind.
     */
    @JsonAnyGetter
    public Map<String, Object> getProperties() {
        return properties;
    }

    /**
     * Gibt an, ob das Property mit dem übergebenen Namen existiert. Die Properties "_links" und "_embedded" gelten
     * jeweils als existierend, wenn Links bzw. eingebette Ressourcen tatsächlich existieren.
     *
     * @param name Der Name des Properties.
     * @return <code>true</code>, wenn das Property existiert, sonst <code>false</code>.
     */
    public boolean hasProperty(final String name) {
        requireNonNull(name);
        if (name.equals(_LINKS)) {
            return !links.isEmpty();
        }
        if (name.equals(_EMBEDDED)) {
            return !embedded.isEmpty();
        }
        return properties.containsKey(name);
    }

    /**
     * Liefert den Wert des Properties mit dem übergebenen Namen zurück.
     * <p>
     * Die Properties "_links" und "_embedded" werden leicht anders behandelt. Sie werden als unmodifiableMap zurück
     * gegeben. Um konsistent mit hasProperty() zu sein, wird <code>null</code> geliefert, falls hasProperty()
     * <code>false</code> liefert.
     *
     * @param name Der Name des Properties.
     * @return <code>true</code>, wenn das Property existiert, sonst <code>false</code>.
     */
    public Object getProperty(final String name) {
        requireNonNull(name);
        if (name.equals(_LINKS)) {
            return getAsProperty(links);
        }
        if (name.equals(_EMBEDDED)) {
            return getAsProperty(embedded);
        }
        return properties.get(name);
    }

    private Map<?, ?> getAsProperty(final Map<?, ?> internalMap) {
        if (internalMap.isEmpty()) {
            return null;
        }
        return Collections.unmodifiableMap(internalMap);
    }

    /**
     * Liefert den einzigen Link mit der angegeben Relation und dem angegebenen Namen zurück.
     *
     * @param relation Die Relation des gesuchten Links.
     * @param name Der Name des gesuchten Links.
     * @return Der gesuchte Link.
     * @throws IllegalArgumentException falls es mehrere oder keinen passenden Link gibt.
     */
    public NamedLink getLink(final String relation, final String name) {
        requireNonNull(name);
        List<NamedLink> namedLinks = filterLinksNamed(name, getLinks(relation));
        checkExcatlyOneLinkFound(namedLinks, relation, name);
        return namedLinks.get(0);
    }

    private List<NamedLink> filterLinksNamed(final String name, final List<Link> links) {
        List<NamedLink> namedLinks = new ArrayList<>();
        for (Link link : links) {
            addLinkIfNamed(name, namedLinks, link);
        }
        return namedLinks;
    }

    private void checkExcatlyOneLinkFound(final List<NamedLink> namedLinks, final String relation, final String name) {
        if (namedLinks.size() > 1) {
            throw new IllegalArgumentException("Mehr als einen Link mit rel="
                + relation
                + " und name="
                + name
                + " gefunden");
        }
        if (namedLinks.isEmpty()) {
            throw new IllegalArgumentException("Keinen Link mit rel=" + relation + " und name=" + name + " gefunden.");
        }
    }

    private void addLinkIfNamed(final String name, final List<NamedLink> namedLinks, final Link link) {
        if (link instanceof NamedLink) {
            NamedLink namedLink = (NamedLink) link;
            if (name.equals(namedLink.getName())) {
                namedLinks.add(namedLink);
            }
        }
    }

}
