package de.hsfulda.et.wbs.core.data;

public interface BestandData extends BestandCreateDto {

    Long getId();

    GroesseData getGroesse();

    KategorieData getKategorie();

    ZielortData getZielort();

}
