package org.example;

import org.example.business.Reflectance;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

public class ReflectanceTest {
    private final List<Double> ref = new ArrayList<>();

    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void buildValidReflectance() {
        ref.clear();
        for(int i=0; i<Reflectance.NUMBER_OF_FEATURES; ++i)
            ref.add(Math.random());

        Reflectance r = Reflectance.build(ref);
        Assert.assertArrayEquals(ref.toArray(), r.getReflectanceList().toArray());
    }

    @Test
    public void buildUnvalidReflectanceTooLowValues() {
        ref.clear();
        ref.add(Math.random());
        exceptionRule.expect(IllegalArgumentException.class);
        Reflectance r = Reflectance.build(ref);
    }

    @Test
    public void buildUnvalidReflectanceNegativeValues() {
        ref.clear();
        for(int i=0; i<Reflectance.NUMBER_OF_FEATURES-1; ++i)
            ref.add(Math.random());
        ref.add(-0.00001);
        exceptionRule.expect(IllegalArgumentException.class);
        Reflectance r = Reflectance.build(ref);
    }

}
