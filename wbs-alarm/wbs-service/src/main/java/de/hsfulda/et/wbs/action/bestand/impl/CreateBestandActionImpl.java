package de.hsfulda.et.wbs.action.bestand.impl;

import de.hsfulda.et.wbs.action.bestand.CreateBestandAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.BestandCreateDto;
import de.hsfulda.et.wbs.core.data.BestandData;
import de.hsfulda.et.wbs.core.exception.BestandAlreadyExistsException;
import de.hsfulda.et.wbs.core.exception.ResourceNotFoundException;
import de.hsfulda.et.wbs.entity.Bestand;
import de.hsfulda.et.wbs.entity.Groesse;
import de.hsfulda.et.wbs.entity.Zielort;
import de.hsfulda.et.wbs.repository.BestandRepository;
import de.hsfulda.et.wbs.repository.GroesseRepository;
import de.hsfulda.et.wbs.repository.ZielortRepository;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Component
public class CreateBestandActionImpl implements CreateBestandAction {

    private final BestandRepository repo;
    private final ZielortRepository zielortRepository;
    private final GroesseRepository groesseRepository;
    private final AccessService accessService;

    public CreateBestandActionImpl(
        BestandRepository repo,
        ZielortRepository zielortRepository,
        GroesseRepository groesseRepository,
        AccessService accessService) {

        this.repo = repo;
        this.zielortRepository = zielortRepository;
        this.groesseRepository = groesseRepository;
        this.accessService = accessService;
    }

    @Override
    public BestandData perform(WbsUser user, Long zielortId, BestandCreateDto bestand) {
        return accessService.hasAccessOnZielort(user, zielortId, () -> {
            checkPreconditions(zielortId, bestand);

            // Durch die Prüfun g der Vorbedingen sind alle Informationen korrekt und vorhanden.
            Optional<Zielort> zielort = zielortRepository.findById(zielortId);
            Optional<Groesse> groesse = groesseRepository.findById(bestand.getGroesseId());

            Bestand saved =
                Bestand.builder()
                    .anzahl(bestand.getAnzahl())
                    .groesse(groesse.get())
                    .build();

            zielort.get().addBestand(saved);
            return repo.save(saved);
        });
    }

    private void checkPreconditions(Long zielortId, BestandCreateDto bestand) {
        Long groesseId = bestand.getGroesseId();

        if (!(groesseRepository.existsById(groesseId))) {
            throw new ResourceNotFoundException("Größe mit ID {0} nicht gefunden.", groesseId);
        }

        if (bestand.getAnzahl() < 0) {
            throw new IllegalArgumentException("Die Anzahl im Bestand darf nicht negativ sein.");
        }

        Optional<BestandData> existing = repo.findAllByZielortIdAndGroesseId(zielortId, groesseId);
        if (existing.isPresent()) {
            throw new BestandAlreadyExistsException("Bestand kann nicht angelegt werden. Dieser existiert bereits.");
        }
    }
}
