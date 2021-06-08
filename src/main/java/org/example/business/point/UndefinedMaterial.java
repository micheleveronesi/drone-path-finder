package org.example.business.point;

public class UndefinedMaterial extends Point{
    public UndefinedMaterial(double latitude, double longitude) {
        super(latitude, longitude);
    }

    @Override
    public int getFrequency() {
        return 100;
    }
}
