package de.hsfulda.et.wbs.service;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.ResourceNotFoundException;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.repository.AccessRepository;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class AccessService {

    private final AccessRepository repo;

    public AccessService(AccessRepository repo) {
        this.repo = repo;
    }

    public <T> T hasAccessOnBenutzer(
            final WbsUser user, final Long benutzerId, final Supplier<T> supplier) {

        Long counts = repo.findTraegerByUserAndBenutzerId(user.getId(), benutzerId);
        return evaluteCount(counts, supplier);
    }

    public HttpEntity<HalJsonResource> hasAccessOnZielort(
            final WbsUser user, final Long zielortId, final Supplier<HttpEntity<HalJsonResource>> supplier) {

        Long counts = repo.findTraegerByUserAndZielortId(user.getId(), zielortId);
        return evaluteCount(counts, supplier);
    }

    public HttpEntity<HalJsonResource> hasAccessOnTraeger(
            final WbsUser user, final Long traegerId, final Supplier<HttpEntity<HalJsonResource>> supplier) {

        Long counts = repo.findTraegerByUserAndTraegerId(user.getId(), traegerId);
        return evaluteCount(counts, supplier);
    }

    public HttpEntity<HalJsonResource> hasAccessOnKategorie(
            final WbsUser user, final Long kategorieId, final Supplier<HttpEntity<HalJsonResource>> supplier) {

        Long counts = repo.findTraegerByUserAndKategorieId(user.getId(), kategorieId);
        return evaluteCount(counts, supplier);
    }

    public HttpEntity<HalJsonResource> hasAccessOnGroesse(
            final WbsUser user, final Long groesseId, final Supplier<HttpEntity<HalJsonResource>> supplier) {
        Long counts = repo.findTraegerByUserAndGroesseId(user.getId(), groesseId);
        return evaluteCount(counts, supplier);
    }

    private <T> T evaluteCount(
            final Long counts, final Supplier<T> supplier) {

        if (counts > 0) {
            return supplier.get();
        }
        throw new ResourceNotFoundException();
    }

}
