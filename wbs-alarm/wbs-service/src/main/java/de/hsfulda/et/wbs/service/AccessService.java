package de.hsfulda.et.wbs.service;

import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.dto.TransaktionDto;
import de.hsfulda.et.wbs.core.exception.ResourceNotFoundException;
import de.hsfulda.et.wbs.repository.AccessRepository;
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
        return evaluteCount(counts, benutzerId, "Benutzer", supplier);
    }

    public <T> T hasAccessOnZielort(
            final WbsUser user, final Long zielortId, final Supplier<T> supplier) {

        Long counts = repo.findTraegerByUserAndZielortId(user.getId(), zielortId);
        return evaluteCount(counts, zielortId, "Zielort", supplier);
    }

    public <T> T hasAccessOnTraeger(
            final WbsUser user, final Long traegerId, final Supplier<T> supplier) {

        Long counts = repo.findTraegerByUserAndTraegerId(user.getId(), traegerId);
        return evaluteCount(counts, traegerId, "Träger", supplier);
    }

    public <T> T hasAccessOnKategorie(
            final WbsUser user, final Long kategorieId, final Supplier<T> supplier) {

        Long counts = repo.findTraegerByUserAndKategorieId(user.getId(), kategorieId);
        return evaluteCount(counts, kategorieId, "Kategorie", supplier);
    }

    public <T> T hasAccessOnGroesse(
            final WbsUser user, final Long groesseId, final Supplier<T> supplier) {
        Long counts = repo.findTraegerByUserAndGroesseId(user.getId(), groesseId);
        return evaluteCount(counts, groesseId, "Größe", supplier);
    }


    public <T> T hasAccessOnBestand(final WbsUser user, final Long bestandId, final Supplier<T> supplier) {
        Long counts = repo.findTraegerByUserAndBestandId(user.getId(), bestandId);
        return evaluteCount(counts, bestandId, "Bestand", supplier);
    }

    public <T> T hasAccessOnTransaktion(WbsUser user, Long transaktionId, final Supplier<T> supplier) {
        Long counts = repo.findTraegerByUserAndTransaktionId(user.getId(), transaktionId);
        return evaluteCount(counts, transaktionId, "Transaktion", supplier);
    }

    public <T> T hasAccessOnTransaktion(WbsUser user, TransaktionDto dto, final Supplier<T> supplier) {

        checkCount(repo.findTraegerByUserAndZielortId(user.getId(), dto.getVon()), dto.getVon(), "Zielort");
        checkCount(repo.findTraegerByUserAndZielortId(user.getId(), dto.getNach()), dto.getNach(), "Zielort");

        dto.getPositions().forEach(p -> {
            checkCount(repo.findTraegerByUserAndGroesseId(user.getId(), p.getGroesse()), p.getGroesse(), "Größe");
        });

        return supplier.get();
    }

    private void checkCount(
            final Long counts, final Long id, final String resource) {

        if (counts == 0) {
            throw new ResourceNotFoundException("{1} mit ID {0} nicht gefunden.", id, resource);
        }
    }

    private <T> T evaluteCount(
            final Long counts, final Long id, final String resource, final Supplier<T> supplier) {
        checkCount(counts, id, resource);

        return supplier.get();
    }
}
