package org.example.business;

import org.example.persistence.FilePersistence;
import org.example.persistence.Instance;
import org.example.persistence.Persistence;
import org.example.prediction.Model;
import org.example.prediction.NeuralNetworkModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe si appoggia al layer di persistenza per interpellare la rete neurale
 * e interagire con la base di persistenza. Inoltre mantiene lo stato attuale dei punti rilevati
 * dal drone, ricevendo quelli nuovi e calcolando il tracciato sulla base di quelli già visti.
 * */
public class Controller {
    /**
     * Rappresenta la rete neurale
     * */
    private final Model neuralNetwork;

    /**
     * URL a cui si trova l'endpoint per le predizioni. Serve per costruire il Model
     * */
    private static final String PREDICTION_URI = "https://127.0.0.1:5000/predict";

    /**
     * Lista di punti già visti, di tutte le classi possibili
     * */
    private final List<Point> points;

    /**
     * Lista dei punti da visitare, potrebbe essere tutti gli alberi di frutta
     * (con il dataset USGS è stato impostato per passare solo dai punti di classe "vegetation")
     * */
    private final List<Point> toVisit;

    /**
     * perimetro da perlustrare
     * */
    private final Perimeter perimeter;

    /**
     * Interfaccia della base di persistenza
     * */
    private final Persistence persistence;

    private Controller(Model neuralNetwork, List<Point> points,
                       List<Point> toVisit, Perimeter perimeter,
                       Persistence persistence) {
        this.neuralNetwork = neuralNetwork;
        this.points = points;
        this.toVisit = toVisit;
        this.perimeter = perimeter;
        this.persistence = persistence;
    }

    /**
     * Factory per costruire oggetti di tipo Controller. L'unico parametro obbligatorio è il perimetro,
     * che va settato con il metodo withPerimeter(). Infine si invoca il metodo build() che ritorna
     * un Controller impostato allo stato attuale della Factory. Se un parametro non è impostato
     * si assume di essere in un contesto reale, dunque tenta di connettersi alla rete neurale e alla
     * base di persistenza.
     * */
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
    /**
     * Prende in input 3 array paralleli, in cui ogni posizione rappresenta un punto analizzato
     * (latitudine, longitudine, riflettanza). Se i 3 array non sono paralleli viene lanciata un'eccezione,
     * altrimenti la lista di riflettanze viene convertita in classi usando la rete neurale, infine tutti i punti
     * con la loro classe vengono aggiunti alla lista di punti visitati
     * @param latitudes lista di latitudini
     * @param longitudes lista di longitudini
     * @param reflectances lista di oggetti riflettanza
     * */
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

    /**
     * Dato in input il punto da cui si vuole partire il metodo ritorna tutti i punti da visitare,
     * per ultimo viene inserito il punto di partenza in modo che si ritorni da dove si è partiti.
     * @return ritorna il percorso da visitare rappresentato da una lista ordinata di punti (ordine di visita)
     * @param latitude la latitudine del punto di partenza
     * @param longitude la longitudine del punto di partenza
     * */
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

    /**
     * Usato per calcolare i nuovi punti da visitare. Al momento seleziona quelli della classe "vegetation".
     * Da modificare nel caso di alleni la rete con altro dataset.
     * */
    private void updateToVisit(){
        toVisit.clear();
        for(Point p : points)
            if(p.getMaterial() == Material.VEGETATION)
                toVisit.add(p);
    }
}
