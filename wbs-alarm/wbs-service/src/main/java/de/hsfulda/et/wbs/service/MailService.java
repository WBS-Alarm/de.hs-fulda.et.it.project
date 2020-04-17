package de.hsfulda.et.wbs.service;

import de.hsfulda.et.wbs.core.Mail;
import de.hsfulda.et.wbs.core.exception.MailConnectionException;
import de.hsfulda.et.wbs.core.exception.MailDeliveryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);

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

            LOGGER.debug("Sending E-Mail \"{}\" to {} ", mail.getSubject(), String.join(", ", mail.getTo()));

            mailSender.send(message);

            LOGGER.info("Send E-Mail \"{}\" to {} ", mail.getSubject(), String.join(", ", mail.getTo()));
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new MailDeliveryException(e, "Beim Senden der Mail {0} ist ein Fehler aufgetreten", mail.toString());
        } catch (MailSendException e) {
            throw new MailConnectionException();
        }
    }
}
