package com.spark.mcp.ai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiService {

    private final ChatClient chatClient;
    private final BlogTools blogTools;

    public String chat(String message) {

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
                .user(message)
                .tools(blogTools)
                .call()
                .content();
    }
}