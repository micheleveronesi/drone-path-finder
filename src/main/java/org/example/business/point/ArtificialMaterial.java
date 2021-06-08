package org.example.business.point;

public class ArtificialMaterial extends Point{

    public ArtificialMaterial(double latitude, double longitude) {
        super(latitude, longitude);
    }

    @Override
    public int getFrequency() {
        return 10;
    }
}
