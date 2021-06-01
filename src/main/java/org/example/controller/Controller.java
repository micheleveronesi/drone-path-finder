package org.example.controller;

import org.example.prediction.Client;
import org.example.prediction.NeuralNetworkClient;
import org.example.server.Server;

import java.io.IOException;

public class Controller {
    private final Client client;
    private final Server server;
    private static final String PREDICTION_URI = "";
    private static final int SERVER_PORT = 5050;

    private Controller(Client c, Server s) {
        this.client = c;
        this.server = s;
    }

    public static Controller buildController() throws IOException {
        Client c = new NeuralNetworkClient(PREDICTION_URI);
        Server s = Server.buildServer(SERVER_PORT);
        return new Controller(c, s);
    }

    public void start() {
        server.start();
    }
}
