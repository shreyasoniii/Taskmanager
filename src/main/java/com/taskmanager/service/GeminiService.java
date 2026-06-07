package com.taskmanager.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

/**
 * Service that calls Google Gemini API to auto-generate task details
 * (description, priority, estimated time) from a task title.
 * Falls back gracefully if the API is unavailable or key is not configured.
 */
@Service
@Slf4j
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    // FIX: Don't declare as 'final' with inline init alongside @RequiredArgsConstructor.
    // Using a plain field here; WebClient is lightweight to create.
    private final WebClient webClient;

    public GeminiService() {
        this.webClient = WebClient.create();
    }

    public GeminiResponse generateTaskDetails(String taskTitle) {
        // Graceful fallback if API key is not configured
        if (apiKey == null || apiKey.isBlank() || apiKey.equals("YOUR_GEMINI_API_KEY")) {
            log.warn("Gemini API key not configured. Returning fallback response.");
            return buildFallback(taskTitle);
        }

        try {
            String prompt = buildPrompt(taskTitle);

            Map<String, Object> requestBody = Map.of(
                "contents", new Object[]{
                    Map.of("parts", new Object[]{
                        Map.of("text", prompt)
                    })
                }
            );

            String fullUrl = apiUrl + "?key=" + apiKey;

            Map response = webClient.post()
                    .uri(fullUrl)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            return parseGeminiResponse(response, taskTitle);

        } catch (Exception e) {
            log.warn("Gemini API call failed: {}. Using fallback.", e.getMessage());
            return buildFallback(taskTitle);
        }
    }

    private String buildPrompt(String taskTitle) {
        return """
                You are a task management assistant. Given the task title below, generate:
                1. A clear, professional task description (2-3 sentences)
                2. A priority level: LOW, MEDIUM, or HIGH
                3. Estimated completion time (e.g., "2 hours", "1 day")

                Task title: "%s"

                Respond in this exact format:
                DESCRIPTION: <your description here>
                PRIORITY: <LOW|MEDIUM|HIGH>
                ESTIMATED_TIME: <time estimate>
                """.formatted(taskTitle);
    }

    @SuppressWarnings("unchecked")
    private GeminiResponse parseGeminiResponse(Map response, String taskTitle) {
        try {
            var candidates = (java.util.List<Map>) response.get("candidates");
            var content = (Map) candidates.get(0).get("content");
            var parts = (java.util.List<Map>) content.get("parts");
            String text = (String) parts.get(0).get("text");

            String description = extractField(text, "DESCRIPTION:");
            String priority    = extractField(text, "PRIORITY:");
            String estimatedTime = extractField(text, "ESTIMATED_TIME:");

            return new GeminiResponse(
                description.isBlank()    ? "No description available" : description,
                priority.isBlank()       ? "MEDIUM" : priority.trim().toUpperCase(),
                estimatedTime.isBlank()  ? "Unknown" : estimatedTime
            );
        } catch (Exception e) {
            log.warn("Failed to parse Gemini response: {}", e.getMessage());
            return buildFallback(taskTitle);
        }
    }

    private String extractField(String text, String fieldName) {
        for (String line : text.split("\n")) {
            if (line.startsWith(fieldName)) {
                return line.substring(fieldName.length()).trim();
            }
        }
        return "";
    }

    private GeminiResponse buildFallback(String taskTitle) {
        return new GeminiResponse(
            "This task involves: " + taskTitle + ". Please add a detailed description.",
            "MEDIUM",
            "Estimate not available"
        );
    }

    // Response record — used by AiController
    public record GeminiResponse(
        String description,
        String priority,
        String estimatedTime
    ) {}
}
