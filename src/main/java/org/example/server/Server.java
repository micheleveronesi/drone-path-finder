package org.example.server;

import com.sun.net.httpserver.HttpServer;
import org.example.business.Controller;
import org.example.business.Perimeter;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    private final HttpServer server;
    private Controller controller;

    private Server(HttpServer server) {
        this.server = server;
        this.controller = null;
    }

    public static Server buildServer(int port) throws IllegalArgumentException, IOException {
        if(port <= 5000)
            throw new IllegalArgumentException("Bad port");
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        Server s = new Server(server);
        server.createContext("/track", new GetTrackHandler(s));
        server.createContext("/data", new PostDataHandler(s));
        server.createContext("/perimeter", new PatchPerimeterHandler(s));
        server.setExecutor(null);
        return s;
    }

    public void start(){
        this.server.start();
    }
    public Controller getController() { return controller; }
    public boolean setControllerPerimeter(Perimeter perimeter) {
        Controller c = null;
        try {
            c = new Controller.Factory().withPerimeter(perimeter).build();
        } catch(Exception e) {
            System.out.println("Error building controller");
            e.printStackTrace();
        }
        if(c != null){
            this.controller = c;
            return true;
        }
        else
            return false;
    }
    public boolean isControllerActive() { return controller != null; }
}
