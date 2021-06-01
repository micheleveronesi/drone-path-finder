package org.example;

import java.util.List;

public class Reflectance {
    private final List<Double> reflectance;
    private static final int NUMBER_OF_FEATURES = 2000;

    private Reflectance(List<Double> reflectance) {
        this.reflectance = reflectance;
    }

    public static Reflectance build(List<Double> features) throws IllegalArgumentException{
        if(features == null || features.size() != NUMBER_OF_FEATURES)
            throw new IllegalArgumentException("Bad features");
        for (Double item : features)
            if(item < 0)
                throw new IllegalArgumentException("Found negative value");
        return new Reflectance(features);
    }

    public List<Double> getReflectanceList() {
        return reflectance;
    }
}
