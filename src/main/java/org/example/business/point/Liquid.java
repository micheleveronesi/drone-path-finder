package org.example.business.point;

public class Liquid extends Point{
    public Liquid(double latitude, double longitude) {
        super(latitude, longitude);
    }

    @Override
    public int getFrequency(){
        return 100;
    }
}
