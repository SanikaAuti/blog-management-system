package com.spark.mcp.ai.controller;


import com.spark.mcp.ai.dto.ChatRequest;
import com.spark.mcp.ai.dto.ChatResponse;
import com.spark.mcp.ai.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final AiService aiService;

    @PostMapping
    public ChatResponse chat(
            @RequestBody ChatRequest request) {

        String response =
                aiService.chat(
                        request.getMessage());

        return new ChatResponse(response);
    }
}