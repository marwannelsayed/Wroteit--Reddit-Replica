package com.example.ThreadsApp.repository;

import com.example.ThreadsApp.model.Thread;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ThreadRepository extends MongoRepository<Thread, Long> {
    List<Thread> findByAuthorId(Long authorId);
    List<Thread> findByCommunityId(Long communityId);

}

