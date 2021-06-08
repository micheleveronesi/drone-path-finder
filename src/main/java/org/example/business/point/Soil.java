package org.example.business.point;

public class Soil extends Point{
    public Soil(double latitude, double longitude) {
        super(latitude, longitude);
    }

    @Override
    public int getFrequency(){
        return 5;
    }
}
