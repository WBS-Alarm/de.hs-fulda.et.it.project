package de.hsfulda.et.wbs.action.kategorie.impl;

import de.hsfulda.et.wbs.action.kategorie.CreateKategorieAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.KategorieData;
import de.hsfulda.et.wbs.core.data.KategorieDto;
import de.hsfulda.et.wbs.entity.Kategorie;
import de.hsfulda.et.wbs.entity.Traeger;
import de.hsfulda.et.wbs.repository.KategorieRepository;
import de.hsfulda.et.wbs.repository.TraegerRepository;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.util.StringUtils.isEmpty;

@Component
public class CreateKategorieActionImpl implements CreateKategorieAction {

    private final KategorieRepository repo;
    private final TraegerRepository traegerRepository;
    private final AccessService accessService;

    public CreateKategorieActionImpl(KategorieRepository repo, TraegerRepository traegerRepository, AccessService accessService) {
        this.repo = repo;
        this.traegerRepository = traegerRepository;
        this.accessService = accessService;
    }

    @Override
    public KategorieData perform(WbsUser user, Long traegerId, KategorieDto kategorie) {
        return accessService.hasAccessOnTraeger(user, traegerId, () -> {

            if (isEmpty(kategorie.getName())) {
                throw new IllegalArgumentException("Name der Kategorie muss angegeben werden.");
            }

            Optional<Traeger> traeger = traegerRepository.findById(traegerId);

            Kategorie saved =
                    Kategorie.builder()
                            .name(kategorie.getName())
                            .aktiv(true)
                            .build();

            Traeger tr = traeger.get();
            tr.addKategorie(saved);
            return repo.save(saved);
        });
    }
}
