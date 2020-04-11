package de.hsfulda.et.wbs.security.resource;

import de.hsfulda.et.wbs.action.benutzer.ResetPasswordAction;
import de.hsfulda.et.wbs.core.exception.MailConnectionException;
import de.hsfulda.et.wbs.security.resource.dto.ResetDtoImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static de.hsfulda.et.wbs.Application.CONTEXT_ROOT;

/**
 * Ressource zum Anfordern einer Mail zur Passwortänderung.
 */
@RestController
@RequestMapping(ResetPasswordResource.PATH)
public class ResetPasswordResource {

    public static final String PATH = CONTEXT_ROOT + "/public/reset-password";

    private final ResetPasswordAction postAction;

    public ResetPasswordResource(ResetPasswordAction postAction) {
        this.postAction = postAction;
    }

    @PostMapping
    ResponseEntity<Void> post(@RequestBody ResetDtoImpl resetDto) {
        try {
            postAction.perform(resetDto.getToken(), resetDto.getPassword());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MailConnectionException e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}