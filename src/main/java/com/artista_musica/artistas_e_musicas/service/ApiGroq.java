package com.artista_musica.artistas_e_musicas.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiGroq {

    public static String InfoArtistaGroq(String texto) {

        Dotenv dotenv = Dotenv.load();

        String prompt = "Me informe dados sobre o artista: " + texto;
        String url = "https://api.groq.com/openai/v1/chat/completions";
        String groqKey = dotenv.get("GROQ_KEY");
        String groqModel = "llama3-70b-8192";

        String json = """
                {
                  "model": "%s",
                  "messages": [
                    {"role": "user", "content": "%s"}
                  ],
                  "temperature": 0.7
                }
                """.formatted(groqModel, prompt);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + groqKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Processar a resposta JSON usando Jackson
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.body());

            // Navegar até choices[0].message.content
            JsonNode contentNode = root.path("choices").get(0).path("message").path("content");

            return contentNode.asText();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Erro na chamada: " + e.getMessage();
        }

    }

    public static String historiaMusicaGroq(String texto) {

        Dotenv dotenv = Dotenv.load();

        String prompt = "Me fale como foi feita a música: " + texto;
        String url = "https://api.groq.com/openai/v1/chat/completions";
        String groqKey = dotenv.get("GROQ_KEY");
        String groqModel = "llama3-70b-8192";

        String json = """
                {
                  "model": "%s",
                  "messages": [
                    {"role": "user", "content": "%s"}
                  ],
                  "temperature": 0.7
                }
                """.formatted(groqModel, prompt);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + groqKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Processar a resposta JSON usando Jackson
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.body());

            // Navegar até choices[0].message.content
            JsonNode contentNode = root.path("choices").get(0).path("message").path("content");

            return contentNode.asText();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Erro na chamada: " + e.getMessage();
        }

    }
}
