package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.Relations;
import de.hsfulda.et.wbs.ResourceTest;
import de.hsfulda.et.wbs.util.UriUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Die Traeger Resource")
class TraegerListResourceTest extends ResourceTest {

    @Autowired
    private TraegerListResource listResource;

    @Autowired
    private TraegerResource resource;

    @DisplayName("wird im Spring Context geladen und gefunden")
    @Test
    void contextLoads() {
        assertThat(resource).isNotNull();
        assertThat(listResource).isNotNull();
    }

    @DisplayName("wird als Liste und embedded angezeigt")
    @Test
    void getAll() throws Exception {
        mockMvc.perform(get(TraegerListResource.PATH)
                .header("Authorization", getTokenAsSuperuser()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self[0].href", is(TraegerListResource.PATH)))
                .andExpect(jsonPath("$._links.self[0].templated", is(false)))
                .andExpect(jsonPath("$._embedded.elemente[0].id", is(1)))
                .andExpect(jsonPath("$._embedded.elemente[0].name", is("Feuerwehr")));
    }

    @DisplayName("kann einen neuen Träger als Admin erstellen")
    @Test
    void postNew() throws Exception {
        mockMvc.perform(post(TraegerListResource.PATH)
                .header("Authorization", getTokenAsSuperuser())
                .content("{\n \"name\": \"Kassel\"\n}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", startsWith("/wbs/traeger/")));
    }

    @DisplayName("kann keinen neuen Träger als lesender Anwender erstellen")
    @Test
    void postNewNoPermissions() throws Exception {
        mockMvc.perform(post(TraegerListResource.PATH)
                .header("Authorization", getToken("Leser"))
                .content("{\n \"name\": \"Haueda\"\n}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @DisplayName("kann keinen neuen Träger ohne Namen als Admin erstellen")
    @Test
    void postNewNoName() throws Exception {
        mockMvc.perform(post(TraegerListResource.PATH)
                .header("Authorization", getTokenAsSuperuser())
                .content("{\n \"name\": \"\"\n}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @DisplayName("kann keinen neuen Träger ohne Request Body als Admin erstellen")
    @Test
    void postNewNoBody() throws Exception {
        mockMvc.perform(post(TraegerListResource.PATH)
                .header("Authorization", getTokenAsSuperuser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("im Einzelnen")
    @Nested
    class TraegerTest {

        @DisplayName("wird angezeigt")
        @Test
        void getKassel() throws Exception {
            String resourceLink = UriUtil.build(Relations.REL_TRAEGER, getTraegerId("Kassel"));
            mockMvc.perform(get(TraegerResource.PATH, getTraegerId("Kassel"))
                    .header("Authorization", getTokenAsSuperuser()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$._links.self[0].href", is(resourceLink)))
                    .andExpect(jsonPath("$._links.self[0].templated", is(false)))
                    .andExpect(jsonPath("$._links.delete[0].href", is(resourceLink)))
                    .andExpect(jsonPath("$._links.delete[0].templated", is(false)))
                    .andExpect(jsonPath("$._links.update[0].href", is(resourceLink)))
                    .andExpect(jsonPath("$._links.update[0].templated", is(false)))
                    .andExpect(jsonPath("$.name", is("Kassel")));
        }

        @DisplayName("wird nicht angezeigt, wenn der Träger nicht vorhanden ist")
        @Test
        void getNotFound() throws Exception {
            mockMvc.perform(get(TraegerResource.PATH, 0L)
                    .header("Authorization", getTokenAsSuperuser()))
                    .andExpect(status().isNotFound());
        }

        @DisplayName("bei Änderungen")
        @Nested
        class TraegerChangeTest {

            @DisplayName("wird erfolgreich der Name geändert")
            @Test
            void putKassel() throws Exception {
                mockMvc.perform(put(TraegerResource.PATH, getTraegerId("Kassel"))
                        .header("Authorization", getTokenAsSuperuser())
                        .content("{\n \"name\": \"Fulda\"\n}")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name", is("Fulda")));
            }

            @DisplayName("wird ohne Angabe von Namen nicht geändert")
            @Test
            void putKasselNoName() throws Exception {
                mockMvc.perform(put(TraegerResource.PATH, getTraegerId("Fulda"))
                        .header("Authorization", getTokenAsSuperuser())
                        .content("{\n \"name\": \"\"\n}")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            }

            @DisplayName("beim Entfernen")
            @Nested
            class TraegerDeleteTest {

                @DisplayName("wird der Träger erfolgreich gelöscht und ist nicht mehr auffindbar")
                @Test
                void deleteKassel() throws Exception {
                    Long fulda = getTraegerId("Fulda");
                    mockMvc.perform(delete(TraegerResource.PATH, fulda)
                            .header("Authorization", getTokenAsSuperuser()))
                            .andExpect(status().isOk());

                    // Danach nicht mehr zu finden
                    mockMvc.perform(get(TraegerResource.PATH, fulda)
                            .header("Authorization", getTokenAsSuperuser()))
                            .andExpect(status().isNotFound());
                }

                @DisplayName("wird der Träger nciht gefunden wenn dieser nicht existiert")
                @Test
                void deleteNotFound() throws Exception {
                    mockMvc.perform(delete(TraegerResource.PATH, 0L)
                            .header("Authorization", getTokenAsSuperuser()))
                            .andExpect(status().isNotFound());
                }

            }

        }
    }
}