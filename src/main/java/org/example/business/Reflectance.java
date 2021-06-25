package org.example.business;

import java.util.List;

/**
 * Rappresenta una firma spettrale di un oggetto. Questa deve essere composta da 2000 valori.
 * */
public class Reflectance {
    private final List<Double> reflectance;
    public static final int NUMBER_OF_FEATURES = 2000;

    private Reflectance(List<Double> reflectance) {
        this.reflectance = reflectance;
    }

    public static Reflectance build(List<Double> features) throws IllegalArgumentException{
        if(features == null || features.size() != NUMBER_OF_FEATURES)
            throw new IllegalArgumentException("Bad features");
        for (Double item : features)
            if(Double.compare(item, 0.0) < 0)
                throw new IllegalArgumentException("Found negative value");
        return new Reflectance(features);
    }

    public List<Double> getReflectanceList() {
        return reflectance;
    }
}
