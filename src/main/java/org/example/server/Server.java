package org.example.server;

import com.sun.net.httpserver.HttpServer;
import org.example.business.Controller;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    private final HttpServer server;

    private Server(HttpServer server) {
        this.server = server;
    }

    public static Server buildServer(int port) throws IllegalArgumentException, IOException {
        if(port <= 5000)
            throw new IllegalArgumentException("Bad port");
        Controller c = Controller.buildController();
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/track", new GetTrackHandler(c));
        server.createContext("/data", new PostDataHandler(c));
        server.setExecutor(null);
        return new Server(server);
    }

    public void start(){
        this.server.start();
    }
}
