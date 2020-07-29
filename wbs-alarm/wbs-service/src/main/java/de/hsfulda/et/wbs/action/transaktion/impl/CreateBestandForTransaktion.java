package de.hsfulda.et.wbs.action.transaktion.impl;

import de.hsfulda.et.wbs.core.data.BestandData;
import de.hsfulda.et.wbs.core.dto.BestandCreateDto;
import de.hsfulda.et.wbs.core.exception.BestandAlreadyExistsException;
import de.hsfulda.et.wbs.core.exception.ResourceNotFoundException;
import de.hsfulda.et.wbs.entity.Bestand;
import de.hsfulda.et.wbs.entity.Groesse;
import de.hsfulda.et.wbs.entity.Zielort;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Component
public class CreateBestandForTransaktion {

    private final TransaktionDao transaktionDao;

    public CreateBestandForTransaktion(TransaktionDao transaktionDao) {
        this.transaktionDao = transaktionDao;
    }

    public BestandData perform(Long zielortId, BestandCreateDto bestand) {
        checkPreconditions(zielortId, bestand);

        // Durch die Prüfun g der Vorbedingen sind alle Informationen korrekt und vorhanden.
        Zielort zielort = transaktionDao.getZielort(zielortId);
        Groesse groesse = transaktionDao.getGroesse(bestand.getGroesseId());

        Bestand saved = Bestand.builder()
                .anzahl(bestand.getAnzahl())
                .groesse(groesse)
                .build();

        zielort.addBestand(saved);
        return transaktionDao.saveBestand(saved);
    }

    private void checkPreconditions(Long zielortId, BestandCreateDto bestand) {
        Long groesseId = bestand.getGroesseId();

        if (!(transaktionDao.existsGroesse(groesseId))) {
            throw new ResourceNotFoundException("Größe mit ID {0} nicht gefunden.", groesseId);
        }

        if (bestand.getAnzahl() < 0) {
            throw new IllegalArgumentException("Die Anzahl im Bestand darf nicht negativ sein.");
        }

        Optional<Bestand> existing = transaktionDao.getBestand(zielortId, groesseId);
        if (existing.isPresent()) {
            throw new BestandAlreadyExistsException("Bestand kann nicht angelegt werden. Dieser existiert bereits.");
        }
    }
}
