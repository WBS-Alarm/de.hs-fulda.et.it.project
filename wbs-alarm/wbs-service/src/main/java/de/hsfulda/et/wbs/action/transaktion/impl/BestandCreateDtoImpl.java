package de.hsfulda.et.wbs.action.transaktion.impl;

import de.hsfulda.et.wbs.core.dto.BestandCreateDto;
import de.hsfulda.et.wbs.core.dto.PositionDto;

class BestandCreateDtoImpl implements BestandCreateDto {

    private final PositionDto positionDto;

    private BestandCreateDtoImpl(PositionDto positionDto) {
        this.positionDto = positionDto;
    }

    public static BestandCreateDto of(PositionDto position) {
        return new BestandCreateDtoImpl(position);
    }


    @Override
    public Long getGroesseId() {
        return positionDto.getGroesse();
    }

    /**
     * Initial wird 0 angegeben, da nach dem Erstellen die Anzahl aus der Transaktion aufgerechnet wird.
     *
     * @return
     */
    @Override
    public Long getAnzahl() {
        return 0L;
    }
}
