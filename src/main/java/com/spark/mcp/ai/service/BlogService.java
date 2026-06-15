package com.spark.mcp.ai.service;


import com.spark.mcp.ai.entity.Blog;
import com.spark.mcp.ai.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository repository;

    public Blog createBlog(String title, String description) {

        Blog blog = Blog.builder()
                .title(title)
                .description(description)
                .build();

        return repository.save(blog);
    }

    public List<Blog> searchBlogs(String title) {
        return repository.findByTitleContainingIgnoreCase(title);
    }

    public List<Blog> getAllBlogs() {
        return repository.findAll();
    }
}