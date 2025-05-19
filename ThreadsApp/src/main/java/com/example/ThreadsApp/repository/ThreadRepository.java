package com.example.ThreadsApp.repository;

import com.example.ThreadsApp.model.Thread;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ThreadRepository extends MongoRepository<Thread, String> {
    List<Thread> findByAuthorId(Long authorId);
    List<Thread> findByCommunityId(String communityId);

}

