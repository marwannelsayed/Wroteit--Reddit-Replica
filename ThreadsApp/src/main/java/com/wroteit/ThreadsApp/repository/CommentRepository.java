package com.wroteit.ThreadsApp.repository;

import com.wroteit.ThreadsApp.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment, String> {
    // Custom query methods can be added here if needed
}

