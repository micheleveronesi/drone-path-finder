package org.example.business.point;

public class OrganicCompound extends Point {
    public OrganicCompound(double latitude, double longitude){
        super(latitude, longitude);
    }

    @Override
    public int getFrequency() {
        return 5;
    }
}
