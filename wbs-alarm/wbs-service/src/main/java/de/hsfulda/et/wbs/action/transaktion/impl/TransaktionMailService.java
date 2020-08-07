package de.hsfulda.et.wbs.action.transaktion.impl;

import de.hsfulda.et.wbs.action.MailService;
import de.hsfulda.et.wbs.core.Mail;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.BenutzerData;
import de.hsfulda.et.wbs.core.data.GroesseData;
import de.hsfulda.et.wbs.core.data.KategorieData;
import de.hsfulda.et.wbs.core.data.ZielortData;
import de.hsfulda.et.wbs.core.dto.BenutzerDto;
import de.hsfulda.et.wbs.core.dto.TransaktionDto;
import de.hsfulda.et.wbs.core.exception.MailConnectionException;
import de.hsfulda.et.wbs.entity.Bestand;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransaktionMailService {

    private final MailService mailService;
    private final TransaktionDao transaktionDao;

    public TransaktionMailService(MailService mailService, TransaktionDao transaktionDao) {
        this.mailService = mailService;
        this.transaktionDao = transaktionDao;
    }

    public void sendMail(WbsUser user, TransaktionDto dto) {

        List<BenutzerData> allEinkaeufer = transaktionDao.getEinkaeufer(user.getId());
        if (allEinkaeufer.isEmpty()) {
            return;
        }

        List<Bestand> bestaende = dto.getPositions()
                .stream()
                .map(p -> transaktionDao.getBestand(dto.getVon(), p.getGroesse()))
                .filter(b -> b.isPresent() && b.map(Bestand::isMailRequired)
                        .orElse(false))
                .map(Optional::get)
                .collect(Collectors.toList());

        if (bestaende.isEmpty()) {
            return;
        }

        try {
            mailService.send(new Mail() {
                @Override
                public String getSubject() {
                    return "WBS-Alarm - Bestandsgrenzen wurden unterschritten.";
                }

                @Override
                public String[] getTo() {
                    return allEinkaeufer.stream()
                            .map(BenutzerDto::getMail)
                            .toArray(String[]::new);
                }

                @Override
                public String getText() {
                    String bestandsliste = bestaende.stream()
                            .map(b -> {
                                ZielortData zielort = b.getZielort();
                                GroesseData groesse = b.getGroesse();
                                KategorieData kategorie = groesse.getKategorie();
                                return " - " + zielort.getName() + ": " + kategorie.getName() + ", Größe: " +
                                        b.getGroesse()
                                                .getName() + "\n";
                            })
                            .collect(Collectors.joining());
                    return "Hallo,\n\nin den folgenden Beständen wurden die Bestansgrenzen unterschritten:\n\n" +
                            bestandsliste + "\n\n" + " Viel Spaß beim Buchen\n " + " Dein WBS-Alarm Team. ";

                }
            });
        } catch (MailConnectionException e) {
            e.printStackTrace();
        }

    }
}
