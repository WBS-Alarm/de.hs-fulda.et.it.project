package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.action.GetGrantedAuthorityListAction;
import de.hsfulda.et.wbs.action.benutzer.DeleteBenutzerAction;
import de.hsfulda.et.wbs.action.benutzer.GetBenutzerAction;
import de.hsfulda.et.wbs.action.benutzer.UpdateBenutzerAction;
import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.BenutzerData;
import de.hsfulda.et.wbs.core.data.GrantedAuthorityData;
import de.hsfulda.et.wbs.http.haljson.BenutzerHalJson;
import de.hsfulda.et.wbs.http.resource.dto.BenutzerDtoImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static de.hsfulda.et.wbs.Application.CONTEXT_ROOT;
import static de.hsfulda.et.wbs.core.HalJsonResource.HAL_JSON;

/**
 * Diese Resource stellt einen Benutzer dar. Hier kann ein Benutzer aufgerufen, bearbeitet und gelöscht (inaktiv
 * gesetzt) werden.
 */
@RestController
@RequestMapping(BenutzerResource.PATH)
public class BenutzerResource {

    public static final String PATH = CONTEXT_ROOT + "/benutzer/{id}";

    private final GetBenutzerAction getAction;
    private final UpdateBenutzerAction putAction;
    private final DeleteBenutzerAction deleteAction;
    private final GetGrantedAuthorityListAction getAuthoritiesAction;

    public BenutzerResource(
            GetBenutzerAction getAction,
            UpdateBenutzerAction putAction,
            DeleteBenutzerAction deleteAction,
            GetGrantedAuthorityListAction getAuthoritiesAction) {
        this.getAction = getAction;
        this.putAction = putAction;
        this.deleteAction = deleteAction;
        this.getAuthoritiesAction = getAuthoritiesAction;
    }

    /**
     * Ermittelt einen Benutzer anhand der ID.
     *
     * @param user angemeldeter Benutzer
     * @param id   ID des Benutzers aus dem Pfad
     * @return gefundenen Träger. Anderfalls 404
     */
    @GetMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('READ_ALL')")
    HttpEntity<HalJsonResource> get(@AuthenticationPrincipal WbsUser user, @PathVariable("id") Long id) {
        BenutzerData benutzer = getAction.perform(user, id);
        List<GrantedAuthorityData> authorities = getAuthoritiesAction.perform(benutzer.getId());

        return new HttpEntity<>(BenutzerHalJson.ofGrantedAuthorities(benutzer, authorities));
    }

    /**
     * Ändert die Werte von einem Benutzer. Hierbei wird die alte Repräsentation vom Benutzer geladen und nur Werte
     * überschrieben die auch geändert werden dürfen. Password, Token und Benutzername bleiben von Änderungen
     * unbetroffen.
     *
     * @param user     angemeldeter Benutzer
     * @param id       ID des Benutzers aus dem Pfad
     * @param benutzer geänderte Werte des Benutzers
     * @return Aktualisierter Benutzer.
     */
    @PutMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    HttpEntity<HalJsonResource> put(
            @AuthenticationPrincipal WbsUser user,
            @PathVariable("id") Long id,
            @RequestBody BenutzerDtoImpl benutzer) {
        return new HttpEntity<>(BenutzerHalJson.of(putAction.perform(user, id, benutzer)));
    }

    /**
     * Benutzer werden nicht gelöscht, sondern nur deaktiviert.
     *
     * @param user angemeldeter Benutzer
     * @param id   ID des Benutzers aus dem Pfad
     * @return Rückmeldung über den Erfolg durch den HttpStatus.
     */
    @DeleteMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    HttpEntity<HalJsonResource> delete(@AuthenticationPrincipal WbsUser user, @PathVariable("id") Long id) {
        deleteAction.perform(user, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}