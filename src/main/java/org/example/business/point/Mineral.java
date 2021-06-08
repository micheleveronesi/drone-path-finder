package org.example.business.point;

public class Mineral extends Point{
    public Mineral(double latitude, double longitude) {
        super(latitude, longitude);
    }

    @Override
    public int getFrequency() {
        return 10;
    }
}
