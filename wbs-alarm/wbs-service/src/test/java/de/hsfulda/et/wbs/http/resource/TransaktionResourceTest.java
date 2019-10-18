package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.ResourceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Die Transaktion Resource")
class TransaktionResourceTest extends ResourceTest {

    @Autowired
    private TransaktionResource resource;

    @DisplayName("wird im Spring Context geladen und gefunden")
    @Test
    void contextLoads() {
        assertThat(resource).isNotNull();
    }

    @DisplayName("Transaktionen werden zu Träger aufgelistet")
    @Test
    void getTransaktionList() throws Exception {
        Long traegerId = getTraegerId(HE_TRAEGER);
        mockMvc.perform(get(TransaktionListResource.PATH, traegerId).header("Authorization", getToken("HelsaUser"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("JSON kann interpretiert werden, invalide Daten.")
    @Test
    void putAendernEinesZielorts() throws Exception {
        Long traegerId = getTraegerId(HE_TRAEGER);
        mockMvc.perform(post(TransaktionListResource.PATH, traegerId)
                .header("Authorization", getToken("HelsaBuchung"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"von\":-12,\"nach\":-10,\"positions\":[{\"groesse\":-1,\"anzahl\":1}]}"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Zielort mit ID -12 nicht gefunden.")));
    }
}