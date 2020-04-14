package de.hsfulda.et.wbs.security.resource;

import de.hsfulda.et.wbs.action.benutzer.ForgotPasswordAction;
import de.hsfulda.et.wbs.core.exception.MailConnectionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static de.hsfulda.et.wbs.Application.CONTEXT_ROOT;

/**
 * Ressource zum Anfordern einer Mail zur Passwortänderung.
 */
@RestController
@RequestMapping(ForgotPasswordResource.PATH)
public class ForgotPasswordResource {

    public static final String PATH = CONTEXT_ROOT + "/public/forgot/{username}";

    private final ForgotPasswordAction postAction;

    public ForgotPasswordResource(ForgotPasswordAction postAction) {
        this.postAction = postAction;
    }

    @PostMapping
    ResponseEntity<Void> post(@PathVariable("username") String username) {
        try {
            postAction.perform(username);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MailConnectionException e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}