package de.hsfulda.et.wbs.core.data;


public interface ZielortData extends ZielortDto {

    Long getId();
    boolean isAuto();
    boolean isAktiv();
    boolean isErfasst();
    TraegerData getTraeger();

}