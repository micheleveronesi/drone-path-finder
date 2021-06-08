package org.example.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.business.Perimeter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class PatchPerimeterHandler implements HttpHandler {
    private final Server server;

    public PatchPerimeterHandler(Server server){
        this.server = server;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        OutputStream os = exchange.getResponseBody();
        String response;
        Gson g = new Gson();
        if (exchange.getRequestMethod().equalsIgnoreCase("patch")) {
            InputStream is = exchange.getRequestBody();
            String input = new String(is.readAllBytes());
            Perimeter p = g.fromJson(input, Perimeter.class);
            response = server.setController(p) ? "success" : "error";
        }
        else
            response = "Only PATCH requests please";
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();
    }
}
