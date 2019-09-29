package de.hsfulda.et.wbs.action.traeger.impl;

import de.hsfulda.et.wbs.action.traeger.CreateTraegerAction;
import de.hsfulda.et.wbs.core.data.TraegerData;
import de.hsfulda.et.wbs.core.dto.TraegerDto;
import de.hsfulda.et.wbs.entity.Traeger;
import de.hsfulda.et.wbs.entity.Zielort;
import de.hsfulda.et.wbs.repository.TraegerRepository;
import org.springframework.stereotype.Component;

import static org.springframework.util.ObjectUtils.isEmpty;

@Component
public class CreateTraegerActionImpl implements CreateTraegerAction {

    private final TraegerRepository repo;

    public CreateTraegerActionImpl(TraegerRepository repo) {
        this.repo = repo;
    }

    @Override
    public TraegerData perform(TraegerDto traeger) {
        if (isEmpty(traeger.getName())) {
            throw new IllegalArgumentException("Name des Trägers muss angegeben werden");
        }

        Traeger tr = Traeger.builder().
                name(traeger.getName())
                .build();

        Zielort.getStandardForNewTraeger().forEach(tr::addZielort);
        return repo.save(tr);
    }
}
