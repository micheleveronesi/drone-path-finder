package org.example.controller;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private final List<Point> nodes;
    private final Point start;

    public Graph(List<Point> nodes, Point start) {
        this.nodes = nodes;
        this.start = start;
    }

    private static int nearestPoint(Point from, List<Point> to) {
        int minIndex = -1;
        double minDistance = -1;
        for (int i=0; i < to.size(); ++i) {
            double currentDistance = calculateCost(from, to.get(i));
            if (Double.compare(currentDistance, minDistance) < 0) {
                minDistance = currentDistance;
                minIndex = i;
            }
        }
        return minIndex;
    }

    private static double calculateCost(Point a, Point b){
        return Math.sqrt(Math.pow((b.getLatitude() - a.getLatitude()), 2) +
                        Math.pow((b.getLongitude() - a.getLongitude()), 2));
    }

    public List<Point> getPath() {
        List<Point> toVisit = new ArrayList<>();
        for(Point p : nodes)
            toVisit.add(p);

        List<Point> path = new ArrayList<>();
        while(!toVisit.isEmpty()) { // O(n^2)
            Point previous = path.isEmpty() ? start : path.get(path.size()-1);
            int next = nearestPoint(previous, toVisit); // O(n)
            path.add(toVisit.remove(next));
        }
        path.add(start);
        return path;
    }
}
