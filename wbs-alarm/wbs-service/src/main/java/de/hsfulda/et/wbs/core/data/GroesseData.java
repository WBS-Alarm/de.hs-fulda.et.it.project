package de.hsfulda.et.wbs.core.data;


public interface GroesseData extends GroesseDto {

    Long getId();
    boolean isAktiv();
    KategorieData getKategorie();

}
