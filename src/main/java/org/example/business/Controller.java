package org.example.business;

import org.example.persistence.FilePersistence;
import org.example.persistence.Instance;
import org.example.persistence.Persistence;
import org.example.prediction.Model;
import org.example.prediction.NeuralNetworkModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private final Model neuralNetwork;
    private static final String PREDICTION_URI = "https://127.0.0.1:5000/predict";
    private final List<Point> points, toVisit;
    private final Perimeter perimeter;
    private final Persistence persistence;

    public Controller(Perimeter perimeter) throws IOException{
        this.neuralNetwork = new NeuralNetworkModel(PREDICTION_URI);
        this.points = new ArrayList<>();
        this.toVisit = new ArrayList<>();
        this.perimeter = perimeter;
        this.persistence = new FilePersistence("database.txt");
    }

    public void updatePoints(List<Double> latitudes,
                             List<Double> longitudes,
                             List<Reflectance> reflectances)
            throws IllegalArgumentException {

        if (latitudes.size() != longitudes.size() ||
                longitudes.size() != reflectances.size()) {
            throw new IllegalArgumentException("Bad sizes");
        }
        int prediction;
        for (int i = 0; i < latitudes.size(); ++i) {
            prediction = neuralNetwork.predict(reflectances.get(i).getReflectanceList());
            points.add(new Point(latitudes.get(i), longitudes.get(i), prediction));
        }
        persistence.saveInstance(new Instance(points));
    }

    public List<Point> getTrack(double latitude, double longitude) {
        Instance lastInstance = persistence.getLastInstance();
        if(lastInstance.isTooOld()) { // non arrivano nuove riflettanze da troppo tempo
            points.clear();
            toVisit.clear();
        }

        if(points.isEmpty())
            return new ArrayList<>(); // rifai il percorso a serpentina, ritorno un percorso vuoto

        // calculate new track
        Point start = new Point(latitude, longitude, -1);
        updateToVisit();
        return new Graph(toVisit, start).getPath();
    }

    private void updateToVisit(){
        toVisit.clear();
        for(Point p : points)
            if(p.getMaterial() == Material.VEGETATION)
                toVisit.add(p);
    }
}
