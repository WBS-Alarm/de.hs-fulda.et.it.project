package de.hsfulda.et.wbs.action.benutzer.impl;

import de.hsfulda.et.wbs.action.benutzer.ForgotPasswordAction;
import de.hsfulda.et.wbs.core.Mail;
import de.hsfulda.et.wbs.core.data.BenutzerData;
import de.hsfulda.et.wbs.core.exception.MailConnectionException;
import de.hsfulda.et.wbs.repository.BenutzerRepository;
import de.hsfulda.et.wbs.service.MailService;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Transactional
@Component
public class ForgotPasswordActionImpl implements ForgotPasswordAction {

    private final MailService mailService;
    private final BenutzerRepository benutzerRepository;

    public ForgotPasswordActionImpl(MailService mailService, BenutzerRepository benutzerRepository) {
        this.mailService = mailService;
        this.benutzerRepository = benutzerRepository;
    }

    @Override
    public void perform(String username) throws MailConnectionException {
        Objects.requireNonNull(username);

        BenutzerData user = benutzerRepository.findByUsername(username);
        if (user != null) {
            UUID uuid = UUID.randomUUID();
            LocalDateTime in10Minutes = LocalDateTime.now()
                    .plusMinutes(10);

            mailService.send(new Mail() {
                @Override
                public String getSubject() {
                    return "Passwort vergessen?";
                }

                @Override
                public String getTo() {
                    return user.getMail();
                }

                @Override
                public String getText() {
                    return "Hallo,\n\n" +
                            "wenn du dein Passwort nicht vergessen hast, ignoriere diese E-Mail einfach oder " +
                            "wende dich an deinen Admin für WBS-Alarm.\n\n" +
                            "Ansonsten kannst du unter folgenden Link dein Passwort neu setzen. Der Link ist 10 " +
                            "Minuten'gültig.\n" + "https://www.wbs-alarm.de/#/update-password/" + uuid + "\n\n" +
                            "Viel Spaß beim Buchen\n" + "Dein WBA-Alarm Team.";
                }
            });

            benutzerRepository.updateForgottokenAndForgotvalid(user.getId(), uuid.toString(), in10Minutes);
        }
    }
}
