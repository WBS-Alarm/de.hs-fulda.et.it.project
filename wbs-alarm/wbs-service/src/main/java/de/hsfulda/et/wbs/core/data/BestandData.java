package de.hsfulda.et.wbs.core.data;

import de.hsfulda.et.wbs.core.dto.BestandCreateDto;

public interface BestandData extends BestandCreateDto {

    Long getId();

    GroesseData getGroesse();

    KategorieData getKategorie();

    ZielortData getZielort();

}
