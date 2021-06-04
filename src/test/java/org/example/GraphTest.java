package org.example;

import org.example.controller.Graph;
import org.example.controller.Point;
import org.junit.Test;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public class GraphTest {
    private final List<Point> NODES = new ArrayList<>();
    private final List<Point> PATH = new ArrayList<>();

    private Point start;

    private void preLoadValues() {
        NODES.clear();
        start = null;
        PATH.clear();

        Point.Factory f = Point.newFactory();

        f.withPrediction(0).withLatitude(0).withLongitude(0);
        start = f.build();
        f.reset();

        f.withPrediction(6).withLatitude(4).withLongitude(0);
        Point p1 = f.build();
        f.reset();

        f.withPrediction(6).withLatitude(4).withLongitude(8);
        Point p2 = f.build();
        f.reset();

        f.withPrediction(6).withLatitude(-5).withLongitude(8);
        Point p3 = f.build();
        f.reset();

        f.withPrediction(6).withLatitude(-5).withLongitude(12);
        Point p4 = f.build();
        f.reset();


        f.withPrediction(6).withLatitude(-5).withLongitude(0);
        Point p5 = f.build();
        f.reset();


        NODES.add(p5);
        NODES.add(p3);
        NODES.add(p1);
        NODES.add(p2);
        NODES.add(p4);

        // expected order
        PATH.add(p1);
        PATH.add(p2);
        PATH.add(p3);
        PATH.add(p4);
        PATH.add(p5);
        PATH.add(start);
    }

    @Test
    public void testCalcPath() {
        preLoadValues();
        Graph g = new Graph(NODES, start);
        List<Point> path = g.getPath();
        Assert.assertTrue(path.size() == PATH.size());
        for(int i=0; i<path.size(); ++i)
            Assert.assertTrue(path.get(i).equals(PATH.get(i)));
    }

}
