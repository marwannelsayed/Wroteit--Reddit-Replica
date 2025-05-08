package com.example.threadapp.repository;

import com.example.threadapp.model.Thread;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ThreadRepository extends MongoRepository<Thread, String> {
    // Custom query methods can be added here if needed
}

