package org.example;

import org.example.business.Graph;
import org.example.business.point.ArtificialMaterial;
import org.example.business.point.Point;
import org.example.business.point.Vegetation;
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

        start = new ArtificialMaterial(0, 0);
        Point p1 = new Vegetation(4, 0);
        Point p2 = new Vegetation(4, 8);
        Point p3 = new Vegetation(-5, 8);
        Point p4 = new Vegetation(-5, 12);
        Point p5 = new Vegetation(-5, 0);

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
