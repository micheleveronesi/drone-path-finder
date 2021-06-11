package org.example.business;

import java.util.HashMap;
import java.util.Map;

public class Point {
    private final double latitude, longitude;
    private final Material material;


    public Point(double latitude, double longitude, int prediction) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.material = Material.intToMaterial(prediction);
    }

    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public Material getMaterial() { return material; }

    public boolean equals(Point p) {
        return Double.compare(this.longitude, p.longitude) == 0 &&
               Double.compare(this.latitude, p.latitude) == 0;
    }

    @Override
    public String toString() {
        return "(" + latitude + "," + longitude + ")";
    }
}
