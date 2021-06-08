package org.example.business;

import java.util.List;

public class Perimeter {
    private final List<Double> latitudes, longitudes;

    public Perimeter(List<Double> latitudes, List<Double> longitudes) throws IllegalArgumentException{
        if(latitudes.size() != 4 || longitudes.size() != 4)
            throw new IllegalArgumentException("Bad perimeter");
        this.latitudes = latitudes;
        this.longitudes = longitudes;
    }


}
