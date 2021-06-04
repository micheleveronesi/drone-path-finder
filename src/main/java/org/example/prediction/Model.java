package org.example.prediction;

import java.util.List;

public interface Model {
    int predict(List<Double> reflectance) throws IllegalArgumentException;
}
