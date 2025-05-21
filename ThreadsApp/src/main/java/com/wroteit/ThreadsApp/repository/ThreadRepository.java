package com.wroteit.ThreadsApp.repository;

import com.wroteit.ThreadsApp.model.Thread;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ThreadRepository extends MongoRepository<Thread, String> {
    List<Thread> findByAuthorId(Long authorId);
    List<Thread> findByCommunityId(String communityId);

}

