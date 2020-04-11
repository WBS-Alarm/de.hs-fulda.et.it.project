package de.hsfulda.et.wbs.action.benutzer;

import de.hsfulda.et.wbs.core.exception.MailConnectionException;

public interface ResetPasswordAction {

    /**
     * Nachdem eine Mail an den Anwender geschickt worden ist, mit der BItte um Passwortrücksetzung, kann über die im
     * Link vergebene UUID ein neues Passwort angegeben werden. Über die UUID wird der Anwender ermittelt und das
     * neue Passwort zugewiesen.
     * Mit setzen des neuen Passworts werden auch Token und Valid-Zeitpunkt zurückgesetzt.
     *
     * @param uuid UUID aus der Rücksetzen-Mail.
     * @param password neues Passwort.
     */
    void perform(String uuid, String password) throws MailConnectionException;
}
