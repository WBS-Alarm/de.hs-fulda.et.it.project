package de.hsfulda.et.wbs.action.transaktion.impl;

import de.hsfulda.et.wbs.core.data.BestandData;
import de.hsfulda.et.wbs.core.dto.BestandDto;
import de.hsfulda.et.wbs.core.exception.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Transactional
@Component
public class UpdateBestandForTransaktion {

    private final TransaktionDao transaktionDao;

    public UpdateBestandForTransaktion(TransaktionDao transaktionDao) {
        this.transaktionDao = transaktionDao;
    }

    public BestandData perform(Long id, BestandDto bestand) {
        if (!transaktionDao.existsBestand(id)) {
            throw new ResourceNotFoundException("Bestand mit ID {0} nicht gefunden.", id);
        }

        if (bestand.getAnzahl() < 0) {
            throw new IllegalArgumentException("Die Anzahl im Bestand darf nicht negativ sein.");
        }

        transaktionDao.updateBestandAnzahl(id, bestand.getAnzahl());
        return transaktionDao.getBestandData(id);
    }
}
