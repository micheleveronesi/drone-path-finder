package org.example.business;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe per rappresentare i punti da visitare come un grafo fully connected.
 * */
public class Graph {
    /**
     * Lista dei nodi del grafo
     * */
    private final List<Point> nodes;

    /**
     * Punto di partenza
     * */
    private final Point start;

    public Graph(List<Point> nodes, Point start) {
        this.nodes = nodes;
        this.start = start;
    }


    /**
     * Metodo per trovare il punto più vicino dal punto "from" selezionato dalla lista "to"
     * @param from punto da cui si vuole calcolare la distanza
     * @param to lista dei punti di possibile destinazione
     * @return ritorna l'indice del punto più vicino a "from" nella lista "to"
     * */
    private static int nearestPoint(Point from, List<Point> to) {
        int minIndex = -1;
        double minDistance = Double.MAX_VALUE;
        for (int i=0; i < to.size(); ++i) {
            double currentDistance = calculateCost(from, to.get(i));
            if (Double.compare(currentDistance, minDistance) < 0) {
                minDistance = currentDistance;
                minIndex = i;
            }
        }
        return minIndex;
    }

    /**
     * Formula del calcolo della distanza tra due punti "a" e "b"
     * TODO: Da estendere per usare con coordinate
     * */
    private static double calculateCost(Point a, Point b){
        return Math.sqrt(Math.pow((b.getLatitude() - a.getLatitude()), 2) +
                        Math.pow((b.getLongitude() - a.getLongitude()), 2));
    }

    /**
     * Algoritmo greedy euristico di calcolo del percorso che sceglie sempre
     * il punto più vicino ad ogni iterazione. Soluzione in O(n^2) al problema
     * del commesso viaggiatore (NP-completo).
     * @return lista di punti ordinati rappresentante il percorso da seguire
     * */
    public List<Point> getPath() {
        List<Point> toVisit = new ArrayList<>();
        for(Point p : nodes)
            toVisit.add(p);

        List<Point> path = new ArrayList<>();
        while(!toVisit.isEmpty()) {
            Point previous = path.isEmpty() ? start : path.get(path.size()-1);
            int next = nearestPoint(previous, toVisit);
            path.add(toVisit.remove(next));
        }
        path.add(start);
        return path;
    }
}
