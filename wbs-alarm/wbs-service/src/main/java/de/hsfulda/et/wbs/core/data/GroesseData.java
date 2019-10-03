package de.hsfulda.et.wbs.core.data;

import de.hsfulda.et.wbs.core.dto.GroesseDto;

public interface GroesseData extends GroesseDto {

    Long getId();

    boolean isAktiv();

    KategorieData getKategorie();

}
