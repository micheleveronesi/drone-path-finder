package org.example.business.point;

public class Vegetation extends Point {
    public Vegetation(double latitude, double longitude) {
        super(latitude, longitude);
    }

    @Override
    public int getFrequency() {
        return 1;
    }
}
