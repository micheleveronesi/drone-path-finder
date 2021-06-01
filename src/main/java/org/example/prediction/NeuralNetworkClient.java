package org.example.prediction;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NeuralNetworkClient implements Client{
    private final URI uri;
    private final HttpClient client;

    public NeuralNetworkClient(String uri){
        this.uri = URI.create(uri);
        this.client = HttpClient.newHttpClient();
    }

    private HttpRequest generateRequest(List<Double> reflectance) {
        Map<String, List<Double>> requestJson = new HashMap<>();
        requestJson.put("reflectance", reflectance);
        String requestBody = requestJson
                .toString()
                .replace('=', ':')
                .replace("reflectance", "\"reflectance\"");
        return HttpRequest.newBuilder()
                .uri(this.uri)
                .timeout(Duration.ofSeconds(10))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
    }

    public int predict(List<Double> reflectance) throws IllegalArgumentException {
        if(reflectance.size() != 2000)
            throw new IllegalArgumentException("Malformed reflectance list");
        HttpRequest request = generateRequest(reflectance);
        int result = -1;
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            result = Integer.parseInt(response.body());
        } catch (Exception e) {
            System.out.println("Predizione fallita");
        }
        return result;
    }
}
