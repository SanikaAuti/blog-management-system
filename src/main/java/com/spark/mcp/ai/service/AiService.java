package com.spark.mcp.ai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class AiService {

    private final ChatClient chatClient;
    private final BlogTools blogTools;
    private final ChatMemory chatMemory;

    public Flux<String> chat(
            String conversationId,
            String message) {

        return chatClient.prompt()
                .system("""
                        You are a blog assistant.
                        
                        Rules:
                        1. Use only the minimum number of tools required.
                        2. For blog creation requests, call only createBlog.
                        3. Do not call searchBlogs or getAllBlogs after creating a blog unless the user explicitly asks.
                        4. For search requests, call only searchBlogs.
                        5. For list requests, call only getAllBlogs.
                        """)
                .advisors(
                        MessageChatMemoryAdvisor.builder(chatMemory)
                                .conversationId(conversationId)
                                .build()
                )
                .user(message)
                .tools(blogTools)
                .stream()
                .content();
    }
}