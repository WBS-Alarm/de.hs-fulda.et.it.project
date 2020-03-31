package de.hsfulda.et.wbs.action.benutzer;

import de.hsfulda.et.wbs.core.exception.MailConnectionException;

public interface ForgotPasswordAction {

    /**
     * Wenn ein Anwender angibt, dass er sein Passwort vergessen hat, kann über diese Aktion eine Mail zu
     * zurücksetzen angefordert werden. Wenn der angegebene Benutzername nicht gefunden wurde darf kein Fehler
     * auftreten, da ansonsten Usernamen geraten werden können.
     * <p>
     * Es wird der Benutzer ermittelt, um ihm eine UUID mit Zeitstempel bis wann das Passwort mit Angabe der UUID
     * änderbar ist.
     *
     * @param username Name des Bentuzers.
     */
    void perform(String username) throws MailConnectionException;
}
