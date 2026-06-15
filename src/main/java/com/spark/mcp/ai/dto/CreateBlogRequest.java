package com.spark.mcp.ai.dto;
import jakarta.validation.constraints.NotBlank;

public record CreateBlogRequest(

        @NotBlank(message = "Title cannot be empty")
        String title,

        @NotBlank(message = "Description cannot be empty")
        String description
) {}