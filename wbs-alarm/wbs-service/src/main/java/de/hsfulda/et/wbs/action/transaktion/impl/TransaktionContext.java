package de.hsfulda.et.wbs.action.transaktion.impl;


import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.BestandData;
import de.hsfulda.et.wbs.core.data.GroesseData;
import de.hsfulda.et.wbs.core.data.ZielortData;
import de.hsfulda.et.wbs.core.exception.ResourceNotFoundException;
import de.hsfulda.et.wbs.core.exception.TransaktionValidationException;
import de.hsfulda.et.wbs.entity.Benutzer;
import de.hsfulda.et.wbs.entity.Bestand;
import de.hsfulda.et.wbs.entity.Groesse;
import de.hsfulda.et.wbs.entity.Zielort;
import de.hsfulda.et.wbs.repository.BenutzerRepository;
import de.hsfulda.et.wbs.repository.BestandRepository;
import de.hsfulda.et.wbs.repository.GroesseRepository;
import de.hsfulda.et.wbs.repository.ZielortRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class TransaktionContext {

    private final BenutzerRepository benutzer;
    private final GroesseRepository groessen;
    private final BestandRepository bestaende;
    private final ZielortRepository zielorte;

    private TransaktionContext(
            BenutzerRepository benutzer,
            GroesseRepository groessen,
            BestandRepository bestaende,
            ZielortRepository zielorte) {
        this.benutzer = benutzer;
        this.groessen = groessen;
        this.bestaende = bestaende;
        this.zielorte = zielorte;
    }

    Benutzer getBenutzer(WbsUser user) {
        Optional<Benutzer> managed = benutzer.findById(user.getId());
        return managed.orElseThrow(() -> new ResourceNotFoundException("Benutzer mit ID {0} nicht gefunden.", user.getId()));
    }

    ZielortData getZielortData(Long id) {
        Optional<ZielortData> managed = findZielortByIdAndAktivIsTrue(id);
        return managed.orElseThrow(() -> new ResourceNotFoundException("Zielort mit ID {0} nicht gefunden.", id));
    }

    Zielort getZielort(Long id) {
        Optional<Zielort> managed = zielorte.findById(id);
        return managed.orElseThrow(() -> new ResourceNotFoundException("Zielort mit ID {0} nicht gefunden.", id));
    }

    private Optional<ZielortData> findZielortByIdAndAktivIsTrue(Long id) {
        return zielorte.findByIdAndAktivIsTrue(id);
    }

    GroesseData getGroesseData(Long id) {
        Optional<GroesseData> managed = findGroesseByIdAndAktivIsTrue(id);
        return managed.orElseThrow(() -> new ResourceNotFoundException("Groesse mit ID {0} nicht gefunden.", id));
    }

    Groesse getGroesse(Long id) {
        Optional<Groesse> managed = groessen.findById(id);
        return managed.orElseThrow(() -> new ResourceNotFoundException("Groesse mit ID {0} nicht gefunden.", id));
    }

    private Optional<GroesseData> findGroesseByIdAndAktivIsTrue(Long id) {
        return groessen.findByIdAndAktivIsTrue(id);
    }

    BestandData getBestandData(Long zielortId, Long groesseId) {
        Optional<BestandData> managed = findBestandByZielortIdAndGroesseId(zielortId, groesseId);
        return managed.orElseThrow(() ->
                new TransaktionValidationException("Für den Zielort {0} wurde kein Bestand gefunden (Größe {1}).",
                        zielortId, groesseId));
    }

    Optional<Bestand> getBestand(Long zielortId, Long groesseId) {
        return bestaende.findByZielortIdAndGroesseId(zielortId, groesseId);
    }

    Bestand getBestand(Long bestandId) {
        Optional<Bestand> managed = bestaende.findById(bestandId);
        return managed.orElseThrow(() -> new ResourceNotFoundException("Bestand mit ID {0} nicht gefunden.", bestandId));
    }

    private Optional<BestandData> findBestandByZielortIdAndGroesseId(Long zielortId, Long groesseId) {
        return bestaende.findByZielortIdAndGroesseIdAsData(zielortId, groesseId);
    }

}
