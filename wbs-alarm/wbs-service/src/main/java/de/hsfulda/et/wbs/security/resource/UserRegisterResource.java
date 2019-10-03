package de.hsfulda.et.wbs.security.resource;

import de.hsfulda.et.wbs.action.benutzer.CreateBenutzerAction;
import de.hsfulda.et.wbs.core.data.BenutzerData;
import de.hsfulda.et.wbs.http.resource.BenutzerResource;
import de.hsfulda.et.wbs.security.resource.dto.BenutzerCreateDtoImpl;
import de.hsfulda.et.wbs.util.UriUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

import static de.hsfulda.et.wbs.Application.CONTEXT_ROOT;

/**
 * In dieser Resource werden Benutzer zu einem Träger registriert. Dies geschieht nur über den Träger Manager.
 */
@RestController
@RequestMapping(UserRegisterResource.PATH)
public class UserRegisterResource {

    public static final String PATH = CONTEXT_ROOT + "/users/register/{traegerId}";

    private final CreateBenutzerAction postAction;

    public UserRegisterResource(CreateBenutzerAction postAction) {
        this.postAction = postAction;
    }

    /**
     * Erst werden die Angaben zum Benutzer gerprüft, ob Name und Password angegeben wurden. Danach wird geprüft, ob der
     * Träger existiert und ob es bereits einen Benutzer mit dem Username bereits vergeben ist.
     *
     * @param traegerId ID des Trägers zu dem der Benutzer angelegt werden soll.
     * @param benutzer Angemeldeter Benutzer.
     * @return Status 201.
     */
    @PostMapping
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    ResponseEntity<Void> post(@PathVariable("traegerId") Long traegerId,
            @RequestBody final BenutzerCreateDtoImpl benutzer) {
        BenutzerData user = postAction.perform(traegerId, benutzer);
        MultiValueMap<String, String> header = new HttpHeaders();
        header.put("Location", Collections.singletonList(UriUtil.build(BenutzerResource.PATH, user.getId())));
        return new ResponseEntity<>(header, HttpStatus.CREATED);
    }

}