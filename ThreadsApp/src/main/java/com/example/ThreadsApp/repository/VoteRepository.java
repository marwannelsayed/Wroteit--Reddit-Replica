package com.example.ThreadsApp.repository;

import com.example.ThreadsApp.model.Vote;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VoteRepository extends MongoRepository<Vote, String> {
    List<Vote> findByTargetIdAndTargetType(String targetId, String targetType);
    // Custom query methods can be added here if needed
}

