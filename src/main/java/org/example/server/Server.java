package org.example.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    private final int port;
    private final HttpServer server;

    private Server(int port, HttpServer server) {
        this.port = port;
        this.server = server;
    }

    public static Server buildServer(int port) throws IllegalArgumentException, IOException {
        if(port <= 5000)
            throw new IllegalArgumentException("Bad port");
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/getTrack", new GetTrackHandler());
        server.createContext("/sendDatas", new PostDataHandler());
        server.setExecutor(null);
        return new Server(port, server);
    }

    public void start(){
        this.server.start();
    }
}
