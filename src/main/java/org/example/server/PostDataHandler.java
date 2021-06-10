package org.example.server;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.business.Controller;
import org.example.business.Reflectance;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PostDataHandler implements HttpHandler {
    private final Server server;

    public PostDataHandler(Server server){
        this.server = server;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        OutputStream os = exchange.getResponseBody();
        String response = "success";
        if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
            InputStream is = exchange.getRequestBody();
            String tmp = new String(is.readAllBytes());
            JsonObject jsonBody = new JsonParser().parse(tmp).getAsJsonObject();
            JsonArray jsonPoints = jsonBody.getAsJsonArray("points");
            List<Double> latitudes = new ArrayList<>();
            List<Double> longitudes = new ArrayList<>();
            List<Reflectance> reflectances = new ArrayList<>();
            for(JsonElement i : jsonPoints) {
                JsonObject current = i.getAsJsonObject();
                double latitude = current.getAsJsonPrimitive("latitude").getAsDouble();
                double longitude = current.getAsJsonPrimitive("longitude").getAsDouble();
                JsonArray jsonReflectance = current.getAsJsonArray("reflectance");
                List<Double> reflectanceList = new ArrayList<>();
                for (JsonElement j : jsonReflectance)
                    reflectanceList.add(j.getAsDouble());
                try {
                    Reflectance r = Reflectance.build(reflectanceList);
                    reflectances.add(r);
                    latitudes.add(latitude);
                    longitudes.add(longitude);
                } catch (IllegalArgumentException e) {
                    response = "some points were bad";
                }
            }
            try {
                server.getController().updatePoints(latitudes, longitudes, reflectances);
            } catch(IllegalArgumentException e) {
                response = "something went wrong while updating controller";
            }
        }
        else
            response = "Only POST requests";

        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("message", response);
        os.write(jsonResponse.toString().getBytes(StandardCharsets.UTF_8));
        os.close();
    }
}
