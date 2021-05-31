package org.example;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class NeuralNetworkClientTest {
    private final NeuralNetworkClient nn = new NeuralNetworkClient("http://127.0.0.1:5000/predict/");
    private final List<Double> reflectanceSample = new ArrayList<>();

    @Before
    public void initializeSample() {
        for (int i=0; i<2000; ++i)
            reflectanceSample.add(Math.random());
    }

    @Test
    public void testPredictCorrect() {
        int result = nn.predict(reflectanceSample);
        assertTrue(result >= 0 && result < 7);
    }

    @Test
    public void testPredictNoServer() {
        int result = nn.predict(reflectanceSample);
        assertTrue(result == -1);
    }
}
