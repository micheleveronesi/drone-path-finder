package org.example;

import org.example.business.Graph;
import org.example.business.Point;
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

        start = new Point(0, 0, -1);
        Point p1 = new Point(4, 0, 6);
        Point p2 = new Point(4, 8, 6);
        Point p3 = new Point(-5, 8, 6);
        Point p4 = new Point(-5, 12, 6);
        Point p5 = new Point(-5, 0, 6);

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
