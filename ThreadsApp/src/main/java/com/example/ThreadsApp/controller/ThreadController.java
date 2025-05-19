package com.example.ThreadsApp.controller;

import com.example.ThreadsApp.model.Thread;
import com.example.ThreadsApp.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/threads")
public class ThreadController {

    private final ThreadService threadService;

    @Autowired
    public ThreadController(ThreadService threadService) {
        this.threadService = threadService;
    }

    @GetMapping
    public List<Thread> getAllThreads() {
        return threadService.getAllThreads();
    }

    @GetMapping("/{id}")
    public Thread getThreadById(@PathVariable String id) {
        return threadService.getThreadById(id);
    }

    @PostMapping
    public ResponseEntity<Thread> createThread(@RequestBody Thread thread) {
        Thread createdThread = threadService.createThread(thread);
        return new ResponseEntity<>(createdThread, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public Thread updateThread(@PathVariable String id, @RequestBody String threadDetails) {
        return threadService.updateThread(id, threadDetails);
    }

    @DeleteMapping("/{id}")
    public String deleteThread(@PathVariable String id) {
        return threadService.deleteThread(id);
    }

    @DeleteMapping("/ban/{id}")
    public String ban(@PathVariable String id) {
        return threadService.banThread(id);
    }

    @GetMapping("/author/{authorId}")
    public List<Thread> getThreadsByAuthorId(@PathVariable Long authorId) {
        return threadService.getThreadsByAuthorId(authorId);
    }

    @GetMapping("/community/{communityId}")
    public List<Thread> getThreadsByCommunityId(@PathVariable String communityId) {
        return threadService.getThreadsByCommunityId(communityId);
    }

}


