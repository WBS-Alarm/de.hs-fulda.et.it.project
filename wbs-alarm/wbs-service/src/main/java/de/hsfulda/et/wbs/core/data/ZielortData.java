package de.hsfulda.et.wbs.core.data;


public interface ZielortData {

    Long getId();

    String getName();

    boolean isAuto();

    boolean isAktiv();

    TraegerData getTraeger();

}
