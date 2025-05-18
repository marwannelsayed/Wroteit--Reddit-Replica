package com.example.threadapp.repository;

import com.example.threadapp.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByThreadId(String threadId);
    // Custom query methods can be added here if needed
}

