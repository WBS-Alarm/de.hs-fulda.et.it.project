package de.hsfulda.et.wbs.core;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

/**
 * Dynamische Hal-Json-Ressourcen-Implementierung. Dadurch können beliebige Typen hiermit dargestellt werden.
 */
public class HalJsonResource {

    public static final String HAL_JSON = "application/hal+json";
    private static final String _EMBEDDED = "_embedded";
    private static final String _LINKS = "_links";

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
     * Liefert die Map zurück in der die Eigenschaften der HalJsonResource enthalten sind.
     *
     * @return Liefert die Map zurück in der die Eigenschaften der HalJsonResource enthalten sind.
     */
    @JsonAnyGetter
    public Map<String, Object> getProperties() {
        return properties;
    }

    /**
     * Fügt einen neuen Link auf die bestehende Liste hinzu.
     *
     * @param link Link der auf die bestehende Liste hinzugefügt werden soll.
     * @throws NullPointerException wenn die {@code link}-Referenz {@code null} war.
     */
    protected final void addLink(final Link link) {
        Objects.requireNonNull(link, "The link must not be null");
        List<Link> links = getLinksForRelation(link.getRel());

        links.add(link);
    }


    private List<Link> getLinksForRelation(final String relation) {
        return links.computeIfAbsent(relation, k -> new ArrayList<>());
    }

    /**
     * Fügt eine neue Resource auf die bestehende Liste hinzu.
     *
     * @param relation Relation der Resource.
     * @param resource Resource die zu den embedded Resourcen hinzugefügt werden soll.
     */
    protected void addEmbeddedResource(final String relation, final HalJsonResource resource) {
        Objects.requireNonNull(resource, "haljson must not be null to be embedded in a haljson");

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
    protected void addEmbeddedResources(final String relation, final List<HalJsonResource> resources) {
        Objects.requireNonNull(resources, "resources must not be null to be embedded in a haljson");
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
    protected void addProperty(final String name, final Object value) {
        Objects.requireNonNull(name, "name of the property of the hal+json Resource must not be null");

        properties.put(name, value);
    }
}
