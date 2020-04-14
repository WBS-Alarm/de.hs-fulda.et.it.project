package de.hsfulda.et.wbs.core.data;

import de.hsfulda.et.wbs.core.dto.BestandCreateDto;

public interface BestandData extends BestandCreateDto {

    Long getId();

    GroesseData getGroesse();

    KategorieData getKategorie();

    ZielortData getZielort();

    /**
     * Muss eine Mail an den EInkäufer gesendet werden, mit dem Hinweis das der Bestand zur neige geht.
     *
     * @return <i><b>true</b></i>, wenn der Bestandsgrenze erreicht wurde.
     */
    boolean isMailRequired();

}
