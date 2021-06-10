package org.example.server;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
        if (exchange.getRequestMethod().equalsIgnoreCase("patch")) {
            InputStream is = exchange.getRequestBody();
            String jsonString = new String(is.readAllBytes());
            JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
            JsonArray points = json.getAsJsonArray("perimeter");
            Perimeter.Factory f = Perimeter.getFactory();
            for(JsonElement i : points) {
                JsonObject current = i.getAsJsonObject();
                double latitude = current.getAsJsonPrimitive("latitude").getAsDouble();
                double longitude = current.getAsJsonPrimitive("longitude").getAsDouble();
                f.addPoint(latitude, longitude);
            }
            try {
                response = server.setControllerPerimeter(f.build()) ? "success" : "error";
            }catch(IllegalStateException e) {
                response = "error creating perimeter";
            }

        }
        else
            response = "Only PATCH requests please";

        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("message", response);
        os.write(jsonResponse.toString().getBytes(StandardCharsets.UTF_8));
        os.close();
    }
}
