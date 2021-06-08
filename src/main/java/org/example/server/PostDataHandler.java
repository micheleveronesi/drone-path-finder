package org.example.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.business.Controller;

import java.io.IOException;

public class PostDataHandler implements HttpHandler {
    private final Server server;

    public PostDataHandler(Server server){
        this.server = server;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }
}
