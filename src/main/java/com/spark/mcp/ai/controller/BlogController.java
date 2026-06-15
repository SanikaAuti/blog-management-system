package com.spark.mcp.ai.controller;

import com.spark.mcp.ai.entity.Blog;
import com.spark.mcp.ai.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @PostMapping
    public Blog createBlog(
            @RequestParam String title,
            @RequestParam String description) {

        return blogService.createBlog(title, description);
    }

    @GetMapping("/search")
    public List<Blog> searchBlogs(@RequestParam String title) {
        return blogService.searchBlogs(title);
    }

    @GetMapping
    public List<Blog> getAllBlogs() {
        return blogService.getAllBlogs();
    }
}