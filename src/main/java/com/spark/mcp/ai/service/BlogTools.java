package com.spark.mcp.ai.service;

import com.spark.mcp.ai.entity.Blog;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BlogTools {

    private final BlogService blogService;

    @Tool(description = "Create a new blog post. Use only when the user wants to create a blog.")
    public String createBlog(
            String title,
            String description) {

        Blog blog = blogService.createBlog(
                title,
                description);

        return "Blog created with id " + blog.getId();
    }

    @Tool(description = "Search blogs by title. Use only when the user asks to find or search blogs.")
    public List<Blog> searchBlogs(String title) {
        return blogService.searchBlogs(title);
    }

    @Tool(description = "List all blogs. Use only when the user explicitly asks to see all blogs.")
    public List<Blog> getAllBlogs() {
        return blogService.getAllBlogs();
    }
}