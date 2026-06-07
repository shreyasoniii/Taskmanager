package com.taskmanager.controller;

import com.taskmanager.dto.response.ApiResponse;
import com.taskmanager.service.GeminiService;
import com.taskmanager.service.GeminiService.GeminiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiController {

    private final GeminiService geminiService;

    @PostMapping("/generate")
    public ResponseEntity<ApiResponse<GeminiResponse>> generateTaskDetails(
            @RequestBody java.util.Map<String, String> body) {

        String title = body.get("title");
        if (title == null || title.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Title is required"));
        }

        GeminiResponse result = geminiService.generateTaskDetails(title);
        return ResponseEntity.ok(ApiResponse.success("AI details generated", result));
    }
}
