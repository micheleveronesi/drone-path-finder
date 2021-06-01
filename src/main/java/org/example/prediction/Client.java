package org.example.prediction;

import java.util.List;

public interface Client {
    int predict(List<Double> reflectance) throws IllegalArgumentException;
}
