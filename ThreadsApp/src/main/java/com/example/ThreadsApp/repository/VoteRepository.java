package com.example.ThreadsApp.repository;

import com.example.ThreadsApp.model.Vote;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends MongoRepository<Vote, Long> {
    List<Vote> findByTargetIdAndTargetType(Long targetId, Vote.TargetType targetType);
    Optional<Vote> findByUserIdAndTargetId(Long userId, Long targetId);
    // Custom query methods can be added here if needed
}

