package de.hsfulda.et.wbs.action.impl;

import de.hsfulda.et.wbs.action.GetTraegerListAction;
import de.hsfulda.et.wbs.core.data.TraegerData;
import de.hsfulda.et.wbs.repository.TraegerRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetTraegerListActionImpl implements GetTraegerListAction {

    private final TraegerRepository repo;

    public GetTraegerListActionImpl(TraegerRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<TraegerData> perform() {
        return repo.findAllAsData();
    }
}
