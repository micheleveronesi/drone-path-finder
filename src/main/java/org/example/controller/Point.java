package org.example.controller;

import java.util.HashMap;
import java.util.Map;

public class Point {
    private final double latitude, longitude;
    private final int prediction;
    private static final Map<Integer, String> classMap = new HashMap<>();

    static {
        classMap.put(0, "artificial_material");
        classMap.put(1, "coating");
        classMap.put(2, "liquid");
        classMap.put(3, "mineral");
        classMap.put(4, "organic_compound");
        classMap.put(5, "soil");
        classMap.put(6, "vegetation");
    }

    private Point(double latitude,
                  double longitude,
                  int prediction) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.prediction = prediction;
    }

    public static class Factory {
        private Double latitude, longitude;
        private Integer prediction;

        private Factory(){
            this.latitude = null;
            this.longitude = null;
            this.prediction = null;
        }

        public Factory withLatitude(double latitude){
            this.latitude = latitude;
            return this;
        }

        public Factory withLongitude(double longitude){
            this.longitude = longitude;
            return this;
        }

        public Factory withPrediction(int prediction) throws IllegalArgumentException{
            if(prediction<0 || prediction>6)
                throw new IllegalArgumentException("Bad class");
            this.prediction = prediction;
            return this;
        }

        public Point build() throws IllegalStateException{
            if(latitude == null || longitude == null || prediction == null)
                throw new IllegalStateException("Missing some parameters");
            return new Point(latitude, longitude, prediction.intValue());
        }

        public void reset() {
            latitude = null;
            longitude = null;
            prediction = null;
        }
    }

    public static Factory newFactory(){
        return new Factory();
    }

    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public int getPredictionClass() { return prediction; }
    public String getPredictionName() { return classMap.get(prediction); }

    public boolean equals(Point p) {
        return Double.compare(this.longitude, p.longitude) == 0 &&
               Double.compare(this.latitude, p.latitude) == 0;
    }
}
