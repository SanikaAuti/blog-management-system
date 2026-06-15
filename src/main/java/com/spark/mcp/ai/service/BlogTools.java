package com.spark.mcp.ai.service;

import com.spark.mcp.ai.dto.CreateBlogRequest;
import com.spark.mcp.ai.entity.Blog;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Slf4j
@Component
@Validated
@RequiredArgsConstructor
public class BlogTools {

    private final BlogService blogService;

    @Tool(description = "Create a new blog post. Use only when the user wants to create a blog.")
    public String createBlog(@Valid CreateBlogRequest request) {

        log.info("TOOL CALLED -> createBlog title={}", request.title());

        Blog blog = blogService.createBlog(
                request.title(),
                request.description());

        return "Blog created with id " + blog.getId();
    }

    @Tool(description = "Search blogs by title. Use only when the user asks to find or search blogs.")
    public List<Blog> searchBlogs(String title) {
        log.info("TOOL CALLED -> searchBlogs title={}", title);
        return blogService.searchBlogs(title);
    }

    @Tool(description = "List all blogs. Use only when the user explicitly asks to see all blogs.")
    public List<Blog> getAllBlogs() {
        log.info("TOOL CALLED -> getAllBlogs");
        return blogService.getAllBlogs();
    }
}