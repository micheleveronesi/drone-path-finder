package org.example.controller;

import java.util.List;

public class Point {
    private final double latitude, longitude;
    private final Reflectance reflectance;

    private Point(double latitude,
                  double longitude,
                  Reflectance reflectance) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.reflectance = reflectance;
    }

    public class PointFactory {
        private Double latitude, longitude;
        private Reflectance reflectance;
        private PointFactory(){
            this.latitude = null;
            this.longitude = null;
            this.reflectance = null;
        }

        public void withLatitude(double latitude){
            this.latitude = latitude;
        }

        public void withLongitude(double longitude){
            this.longitude = longitude;
        }

        public void withReflectance(List<Double> reflectance) throws IllegalArgumentException{
            Reflectance ref = Reflectance.build(reflectance);
            this.reflectance = ref;
        }

        public Point build() throws IllegalStateException{
            if(latitude == null || longitude == null || reflectance == null)
                throw new IllegalStateException("Missing some parameters");
            return new Point(latitude, longitude, reflectance);
        }
    }

    public PointFactory newFactory(){
        return new PointFactory();
    }

    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public Reflectance getReflectance() { return reflectance; }

}
