package org.example.business;

import org.example.business.point.*;
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

    private Controller(Model c, Perimeter perimeter) {
        this.neuralNetwork = c;
        this.points = new ArrayList<>();
        this.toVisit = new ArrayList<>();
        this.perimeter = perimeter;
    }

    public static Controller buildController(Perimeter perimeter) throws IOException {
        return new Controller(new NeuralNetworkModel(PREDICTION_URI), perimeter);
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
            points.add(buildPoint(prediction, latitudes.get(i), longitudes.get(i)));
        }
    }

    public List<Point> getTrack(double latitude, double longitude) {
        if (points.isEmpty())
            return calculateSerpentineTrack();
        Point start = buildPoint(-1, latitude, longitude);
        updateToVisit();
        return new Graph(toVisit, start).getPath();
    }

    public static Point buildPoint(int prediction, double latitute, double longitude) {
        switch(prediction){
            case 0: return new ArtificialMaterial(latitute, longitude);
            case 1: return new Coating(latitute, longitude);
            case 2: return new Liquid(latitute, longitude);
            case 3: return new Mineral(latitute, longitude);
            case 4: return new OrganicCompound(latitute, longitude);
            case 5: return new Soil(latitute, longitude);
            case 6: return new Vegetation(latitute, longitude);
            default: return new UndefinedMaterial(latitute, longitude);
        }
    }

    private void updateToVisit(){
        toVisit.clear();
        // TODO: calcolo punti da visitare all'istante dell'invocazione
    }

    private List<Point> calculateSerpentineTrack() {
        List<Point> line1 = new ArrayList<>();
        List<Point> line2 = new ArrayList<>();


        List<Point> serpentineTrack = new ArrayList<>();
        for(int i = 0; i < line1.size(); ++i) {
            serpentineTrack.add(line1.get(i));
            serpentineTrack.add(line2.get(i));
        }
        return serpentineTrack;
    }
}
