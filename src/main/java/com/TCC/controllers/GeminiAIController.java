package com.TCC.controllers;

import com.TCC.services.GeminiAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class GeminiAIController {

    @Autowired
    private GeminiAIService geminiAIService;

    @GetMapping("/suggestions")
    public String getEventSuggestions(@RequestParam String prompt) {
        try {
            return geminiAIService.getSuggestions(prompt);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Erro ao obter sugestões: " + e.getMessage();
        }
    }
}
