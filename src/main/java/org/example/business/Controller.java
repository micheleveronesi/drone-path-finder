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
    private final Model model;
    private static final String PREDICTION_URI = "https://127.0.0.1:5000/predict";
    private final List<Point> points, toVisit;
    private final Perimeter perimeter;
    private final Persistence persistence;

    private Controller(Model model, List<Point> points,
                       List<Point> toVisit, Perimeter perimeter,
                       Persistence persistence) {
        this.model = model;
        this.points = points;
        this.toVisit = toVisit;
        this.perimeter = perimeter;
        this.persistence = persistence;
    }

    public static class Factory {
        private Model model;
        private List<Point> points, toVisit;
        private Perimeter perimeter;
        private Persistence persistence;

        public Factory() {
            this.model = null;
            this.points = null;
            this.toVisit = null;
            this.perimeter = null;
            this.persistence = null;
        }

        public Factory withModel(Model model) {
            this.model = model;
            return this;
        }

        public Factory withPoints(List<Point> points) {
            this.points = points;
            return this;
        }

        public Factory withPointsToVisit(List<Point> toVisit) {
            this.toVisit = toVisit;
            return this;
        }

        public Factory withPerimeter(Perimeter perimeter) {
            this.perimeter = perimeter;
            return this;
        }

        public Factory withPersistence(Persistence persistence) {
            this.persistence = persistence;
            return this;
        }

        public Controller build() throws IllegalStateException{
            if(perimeter == null)
                throw new IllegalStateException("Missing perimeter");

            if(points == null)
                points = new ArrayList<>();

            if(toVisit == null)
                toVisit = new ArrayList<>();

            if(model == null)
                model = new NeuralNetworkModel(PREDICTION_URI);

            if(persistence == null)
                persistence = new FilePersistence("database.txt");

            return new Controller(model, points, toVisit, perimeter, persistence);
        }

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
            prediction = model.predict(reflectances.get(i).getReflectanceList());
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
