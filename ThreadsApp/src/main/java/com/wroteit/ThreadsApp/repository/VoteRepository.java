package com.wroteit.ThreadsApp.repository;

import com.wroteit.ThreadsApp.model.Vote;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends MongoRepository<Vote, String> {
    List<Vote> findByTargetIdAndTargetType(Long targetId, Vote.TargetType targetType);
    Optional<Vote> findByUserIdAndTargetId(Long userId, String targetId);

    List<Vote> findByTargetId(String targetId);
    // Custom query methods can be added here if needed
}

