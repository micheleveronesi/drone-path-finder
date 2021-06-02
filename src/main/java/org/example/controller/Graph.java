package org.example.controller;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private class Arch{
        private final double cost;
        private final Point a, b;

        public Arch(Point a, Point b) {
            this.a = a;
            this.b = b;
            cost = calculateCost();
        }

        private double calculateCost(){
            return Math.sqrt(
                    Math.pow((b.getLatitude() - a.getLatitude()), 2) +
                    Math.pow((b.getLongitude() - a.getLongitude()), 2));
        }

        public double getCost() {
            return cost;
        }
    }

    private final List<Arch> archs;
    private final List<Point> nodes;
    private final Point start;

    public Graph(List<Point> nodes, Point start) {
        this.archs = new ArrayList<>();
        this.nodes = nodes;
        this.start = start;
        generateArchs();
    }

    private void generateArchs() {
        for (int i=0; i < nodes.size(); ++i)
            for (int j = i+1; j < nodes.size(); ++i)
                archs.add(new Arch(nodes.get(i), nodes.get(j)));
    }

    public List<Point> getPath() {
        // TODO: algoritmo calcolo percorso
        return null;
    }
}
