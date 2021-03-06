package ru.internship.oAuth.util;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class HTTPRequestProvider {


    private final HttpClient httpClient;


    public HTTPRequestProvider() {
        httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
    }

    /**
     * Sends get request to the specified URI
     * @param uri
     * @return response
     */
    public HttpResponse<String> sendGetRequest(String uri) {
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response;
        } catch (IOException | InterruptedException ioException) {
            return null;
        }
    }

    /**
     * Sends get request to the specified URI with the map of parameters
     * @param uri - URI
     * @param body - Map of the parameters
     * @return response
     */
    public HttpResponse<String> sendPostRequest(String uri, Map<String, String> body) {
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .POST(mapToDataConverter(body))
                .uri(URI.create(uri))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response;
        } catch (IOException | InterruptedException ioException) {
            return null;
        }
    }


    /**
     * Converts java.util.Map to the request body for the HTTP POST request
     * @param body
     * @return request body
     */
    private java.net.http.HttpRequest.BodyPublisher mapToDataConverter(Map<String, String> body) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : body.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
        }
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }


}
