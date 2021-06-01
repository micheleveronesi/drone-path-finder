package org.example.server;

import com.sun.net.httpserver.HttpServer;
import org.example.controller.Controller;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    private final int port;
    private final HttpServer server;
    private final Controller controller;

    private Server(int port, HttpServer server, Controller controller) {
        this.port = port;
        this.server = server;
        this.controller = controller;
    }

    public static Server buildServer(int port) throws IllegalArgumentException, IOException {
        if(port <= 5000)
            throw new IllegalArgumentException("Bad port");
        Controller c = Controller.buildController();
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/getTrack", new GetTrackHandler(c));
        server.createContext("/sendDatas", new PostDataHandler(c));
        server.setExecutor(null);
        return new Server(port, server, c);
    }

    public void start(){
        this.server.start();
    }
}
