package org.example.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.controller.Controller;

import java.io.IOException;
import java.io.OutputStream;

public class GetTrackHandler implements HttpHandler {
    private final Controller c;

    public GetTrackHandler(Controller c){
        this.c = c;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "Hello world!";
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
