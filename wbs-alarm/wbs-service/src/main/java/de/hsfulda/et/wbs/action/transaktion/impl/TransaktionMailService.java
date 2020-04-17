package de.hsfulda.et.wbs.action.transaktion.impl;

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
import de.hsfulda.et.wbs.repository.BenutzerRepository;
import de.hsfulda.et.wbs.repository.BestandRepository;
import de.hsfulda.et.wbs.service.MailService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransaktionMailService {

    private final MailService mailService;
    private final BenutzerRepository benutzerRepository;
    private final BestandRepository bestandRepository;

    public TransaktionMailService(MailService mailService, BenutzerRepository benutzerRepository,
            BestandRepository bestandRepository) {
        this.mailService = mailService;
        this.benutzerRepository = benutzerRepository;
        this.bestandRepository = bestandRepository;
    }

    public void sendMail(WbsUser user, TransaktionDto dto) {

        List<BenutzerData> allEinkaeufer = benutzerRepository.findAllEinkaeuferByUserId(user.getId());
        if (allEinkaeufer.isEmpty()) {
            return;
        }

        List<Bestand> bestaende = dto.getPositions()
                .stream()
                .map(p -> bestandRepository.findByZielortIdAndGroesseId(dto.getVon(), p.getGroesse()))
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
