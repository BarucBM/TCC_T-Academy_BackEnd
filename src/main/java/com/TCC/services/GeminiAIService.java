package com.TCC.services;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class GeminiAIService {

    private final String apiKey = "AIzaSyCRsyI1wQWFoF5VIkweIki8bfHSnk-wXqk"; // substituir
    private final String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + apiKey;

    public String getSuggestions(String prompt) throws IOException, InterruptedException {
        String requestBody = String.format(
                "{\"contents\":[{\"parts\":[{\"text\":\"%s\"}]}]}", prompt
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());

        if (response.statusCode() != 200) {
            throw new IOException("Erro na chamada à API: Código de Status: " + response.statusCode() + ", Corpo da Resposta: " + response.body());
        }

        return response.body();
    }
}
