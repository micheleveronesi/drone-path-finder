package org.example;

import org.example.business.Perimeter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

public class PerimeterTest {
    private final List<Double> latitudes = new ArrayList<>(),
            longitudes = new ArrayList<>();

    public void initializeListsRectangle() {
        latitudes.clear();
        longitudes.clear();
        latitudes.add(3.0);
        latitudes.add(9.0);
        latitudes.add(9.0);
        latitudes.add(3.0);
        longitudes.add(2.0);
        longitudes.add(2.0);
        longitudes.add(7.0);
        longitudes.add(7.0);
    }

    public void initializeListsNotRectangle() {
        latitudes.clear();
        longitudes.clear();
        latitudes.add(4.3);
        latitudes.add(2.3);
        latitudes.add(9.0);
        latitudes.add(3.0);
        longitudes.add(2.0);
        longitudes.add(3.0);
        longitudes.add(7.0);
        longitudes.add(74.0);
    }

    @Test
    public void buildPerimeterCorrect() {
        initializeListsRectangle();
        Perimeter.Factory factory = Perimeter.getFactory();
        for(int i=0; i<latitudes.size(); ++i)
            factory.addPoint(latitudes.get(i), longitudes.get(i));
        Perimeter p = factory.build();

        Assert.assertTrue(Double.compare(p.getA().getLatitude(), latitudes.get(0)) == 0);
        Assert.assertTrue(Double.compare(p.getA().getLongitude(), longitudes.get(0)) == 0);
        Assert.assertTrue(Double.compare(p.getB().getLatitude(), latitudes.get(1)) == 0);
        Assert.assertTrue(Double.compare(p.getB().getLongitude(), longitudes.get(1)) == 0);
        Assert.assertTrue(Double.compare(p.getC().getLatitude(), latitudes.get(2)) == 0);
        Assert.assertTrue(Double.compare(p.getC().getLongitude(), longitudes.get(2)) == 0);
        Assert.assertTrue(Double.compare(p.getD().getLatitude(), latitudes.get(3)) == 0);
        Assert.assertTrue(Double.compare(p.getD().getLongitude(), longitudes.get(3)) == 0);
    }

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void buildPerimeterNotARectangle() {
        initializeListsNotRectangle();
        Perimeter.Factory f = Perimeter.getFactory();
        for(int i=0; i<latitudes.size(); ++i)
            f.addPoint(latitudes.get(i), longitudes.get(i));
        exceptionRule.expect(IllegalStateException.class);
        exceptionRule.expectMessage("This is not a rectangle");
        Perimeter p = f.build();
    }

    @Test
    public void notFourPoints() {
        Perimeter.Factory f = Perimeter.getFactory();
        f.addPoint(0, 0);
        exceptionRule.expect(IllegalStateException.class);
        exceptionRule.expectMessage("Points must be four in a rectangle perimeter");
        Perimeter p = f.build();
    }
}
