package org.example;

import org.example.business.Controller;
import org.example.business.Perimeter;
import org.example.business.Point;
import org.example.persistence.PersistenceMock;
import org.example.prediction.MockModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

public class ControllerTest {
    private Perimeter p = null;

    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void initializePerimeter() {
        this.p = null;
        List<Double> latitudes = new ArrayList<>(),
                longitudes = new ArrayList<>();
        latitudes.add(3.0);
        latitudes.add(9.0);
        latitudes.add(9.0);
        latitudes.add(3.0);
        longitudes.add(2.0);
        longitudes.add(2.0);
        longitudes.add(7.0);
        longitudes.add(7.0);
        Perimeter.Factory fp = Perimeter.getFactory();
        for(int i=0; i<latitudes.size(); ++i)
            fp.addPoint(latitudes.get(i), longitudes.get(i));
        this.p = fp.build();
    }

    @Test
    public void buildControllerCorrect() {
        Controller.Factory f = new Controller.Factory();
        f.withModel(new MockModel())
        .withPersistence(new PersistenceMock())
        .withPoints(new ArrayList<>())
        .withPointsToVisit(new ArrayList<>())
        .withPerimeter(this.p);
        Controller c = f.build();
        Assert.assertTrue(c != null);
    }

    @Test
    public void buildControllerMissingPerimeter() {
        exceptionRule.expect(IllegalStateException.class);
        Controller.Factory f = new Controller.Factory();
        f.build();
    }

    @Test
    public void buildControllerOnlyPerimeter() {
        Controller.Factory f = new Controller.Factory();
        f.withPerimeter(this.p);
        Controller c = f.build();
        Assert.assertTrue(c != null);
    }

    @Test
    public void getEmptyTrackTest() {
        Controller.Factory f = new Controller.Factory();
        f.withModel(new MockModel())
                .withPersistence(new PersistenceMock())
                .withPoints(new ArrayList<>())
                .withPointsToVisit(new ArrayList<>())
                .withPerimeter(this.p);
        Controller c = f.build();
        List<Point> track = c.getTrack(0, 0);
        Assert.assertTrue(track != null);
    }
}
