package de.hsfulda.et.wbs.service;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.User;
import de.hsfulda.et.wbs.repository.TraegerRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AccessService {

    private final TraegerRepository traegerRepository;

    public AccessService(TraegerRepository traegerRepository) {
        this.traegerRepository = traegerRepository;
    }

    public HttpEntity<HalJsonResource> hasAccessOnBenutzer(
            final User user, final Long benutzerId, final Supplier<HttpEntity<HalJsonResource>> supplier) {

        Long counts = traegerRepository.findTraegerByUserAndBenutzerId(user.getId(), benutzerId);
        return evaluteCount(counts, supplier);
    }

    public HttpEntity<HalJsonResource> hasAccessOnZielort(
            final User user, final Long zielortId, final Supplier<HttpEntity<HalJsonResource>> supplier) {

        Long counts = traegerRepository.findTraegerByUserAndZielortId(user.getId(), zielortId);
        return evaluteCount(counts, supplier);
    }

    public HttpEntity<HalJsonResource> hasAccessOnTraeger(
            final User user, final Long traegerId, final Supplier<HttpEntity<HalJsonResource>> supplier) {

        Long counts = traegerRepository.findTraegerByUserAndTraegerId(user.getId(), traegerId);
        return evaluteCount(counts, supplier);
    }

    public HttpEntity<HalJsonResource> hasAccessOnKategorie(
            final User user, final Long kategorieId, final Supplier<HttpEntity<HalJsonResource>> supplier) {

        Long counts = traegerRepository.findTraegerByUserAndKategorieId(user.getId(), kategorieId);
        return evaluteCount(counts, supplier);
    }

    private HttpEntity<HalJsonResource> evaluteCount(
            final Long counts, final Supplier<HttpEntity<HalJsonResource>> supplier) {

        if (counts > 0) {
            return supplier.get();
        }
        return new ResponseEntity<>(NOT_FOUND);
    }

}
