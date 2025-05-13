package com.example.threadapp.repository;

import com.example.threadapp.model.Thread;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ThreadRepository extends MongoRepository<Thread, String> {
    List<Thread> findByAuthorId(String authorId);
    List<Thread> findAllByOrderByUpvotesDesc();
    List<Thread> findAllByOrderByDownvotesDesc();
    List<Thread> findAllByOrderByCreatedAtDesc();
    List<Thread> findAllByOrderByCreatedAtAsc();
}

