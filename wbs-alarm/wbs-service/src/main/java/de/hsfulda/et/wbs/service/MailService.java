package de.hsfulda.et.wbs.service;

import de.hsfulda.et.wbs.core.Mail;
import de.hsfulda.et.wbs.core.exception.MailConnectionException;
import de.hsfulda.et.wbs.core.exception.MailDeliveryException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class MailService {

    @Value("${wbs.mail.active}")
    private boolean active;
    @Value("${spring.mail.username}")
    private String fromMailAdress;
    @Value("${wbs.mail.personal}")
    private String fromMailPersonal;

    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void send(Mail mail) throws MailConnectionException {
        if (!active) {
            return;
        }
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setSubject(mail.getSubject());
            helper.setFrom(new InternetAddress(fromMailAdress, fromMailPersonal));
            helper.setTo(mail.getTo());
            helper.setText(mail.getText());

            mailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new MailDeliveryException(e, "Beim Senden der Mail {0} ist ein Fehler aufgetreten", mail.toString());
        } catch (MailSendException e) {
            throw new MailConnectionException();
        }
    }
}
