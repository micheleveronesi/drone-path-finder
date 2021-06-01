package org.example.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.controller.Controller;

import java.io.IOException;

public class PostDataHandler implements HttpHandler {
    private final Controller c;

    public PostDataHandler(Controller c){
        this.c = c;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }
}
