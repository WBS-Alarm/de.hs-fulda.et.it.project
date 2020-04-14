package de.hsfulda.et.wbs.action.benutzer.impl;

import de.hsfulda.et.wbs.action.benutzer.ResetPasswordAction;
import de.hsfulda.et.wbs.core.Mail;
import de.hsfulda.et.wbs.core.data.BenutzerData;
import de.hsfulda.et.wbs.core.exception.MailConnectionException;
import de.hsfulda.et.wbs.repository.BenutzerRepository;
import de.hsfulda.et.wbs.security.Password;
import de.hsfulda.et.wbs.service.MailService;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.springframework.util.StringUtils.isEmpty;

@Transactional
@Component
public class ResetPasswordActionImpl implements ResetPasswordAction {

    private final MailService mailService;
    private final BenutzerRepository benutzerRepository;

    public ResetPasswordActionImpl(MailService mailService, BenutzerRepository benutzerRepository) {
        this.mailService = mailService;
        this.benutzerRepository = benutzerRepository;
    }

    @Override
    public void perform(String uuid, String password) throws MailConnectionException {
        if (isEmpty(uuid) || isEmpty(password)) {
            throw new IllegalArgumentException("Es wurden nicht alle erforderlichen Informationmen angegeben.");
        }

        Optional<BenutzerData> user = benutzerRepository.findByUsernameByToken(uuid);

        if (user.isPresent()) {
            BenutzerData b = user.get();
            if (isTokenValid(b)) {
                resetPassword(password, b);
            }
        } else {
            throw new IllegalArgumentException("Es wurden nicht alle erforderlichen Informationmen angegeben.");
        }
    }

    private void resetPassword(String password, BenutzerData user) throws MailConnectionException {
        benutzerRepository.updateNewPassword(user.getId(), Password.hashPassword(password));

        mailService.send(new Mail() {
            @Override
            public String getSubject() {
                return "Passwort wurde erfolgreich gesetzt.";
            }

            @Override
            public String[] getTo() {
                return new String[]{user.getMail()};
            }

            @Override
            public String getText() {
                return "Hallo,\n\n" +
                        "dein Passwort wurde erfolgreich gesetzt. Wenn du dein Passwort nicht geändert hast, " +
                        "wende dich bitte an uns." + "\n\n" + "Viel Spaß beim Buchen\n" + "Dein WBA-Alarm Team.";
            }
        });
    }

    private boolean isTokenValid(BenutzerData b) {
        return b.getValid() != null && LocalDateTime.now()
                .isBefore(b.getValid());
    }
}
