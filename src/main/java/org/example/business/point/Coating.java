package org.example.business.point;

public class Coating extends Point {
    public Coating(double latitude, double longitude) {
        super(latitude, longitude);
    }

    @Override
    public int getFrequency() {
        return 10;
    }
}
