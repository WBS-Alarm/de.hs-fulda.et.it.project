package de.hsfulda.et.wbs.action.groesse.impl;

import de.hsfulda.et.wbs.action.groesse.CreateGroesseAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.GroesseData;
import de.hsfulda.et.wbs.core.data.GroesseDto;
import de.hsfulda.et.wbs.entity.Groesse;
import de.hsfulda.et.wbs.entity.Kategorie;
import de.hsfulda.et.wbs.repository.GroesseRepository;
import de.hsfulda.et.wbs.repository.KategorieRepository;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.util.StringUtils.isEmpty;

@Component
public class CreateGroesseActionImpl implements CreateGroesseAction {

    private final GroesseRepository repo;
    private final KategorieRepository kategorieRepository;
    private final AccessService accessService;

    public CreateGroesseActionImpl(GroesseRepository repo, KategorieRepository kategorieRepository, AccessService accessService) {
        this.repo = repo;
        this.kategorieRepository = kategorieRepository;
        this.accessService = accessService;
    }

    @Override
    public GroesseData perform(WbsUser user, Long kategorieId, GroesseDto groesse) {
        return accessService.hasAccessOnKategorie(user, kategorieId, () -> {
            if (isEmpty(groesse.getName())) {
                throw new IllegalArgumentException("Name der Größe muss angegeben werden.");
            }

            Optional<Kategorie> kategorie = kategorieRepository.findById(kategorieId);

            Groesse saved =
                    Groesse.builder()
                            .name(groesse.getName())
                            .aktiv(true)
                            .build();

            Kategorie tr = kategorie.get();
            tr.addGroesse(saved);
            return repo.save(saved);
        });
    }
}
