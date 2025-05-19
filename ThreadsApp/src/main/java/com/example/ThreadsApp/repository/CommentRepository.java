package com.example.ThreadsApp.repository;

import com.example.ThreadsApp.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, Long> {
    // Custom query methods can be added here if needed
}

