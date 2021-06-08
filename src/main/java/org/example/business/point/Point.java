package org.example.business.point;

public abstract class Point {
    private final double latitude, longitude;

    public Point(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }

    public boolean equals(Point p) {
        return Double.compare(this.longitude, p.longitude) == 0 &&
               Double.compare(this.latitude, p.latitude) == 0;
    }

    @Override
    public String toString() {
        return "(" + latitude + "," + longitude + ")";
    }

    public abstract int getFrequency();
}
