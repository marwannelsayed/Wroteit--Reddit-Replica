package com.example.ThreadsApp.controller;

import com.example.ThreadsApp.model.Thread;
import com.example.ThreadsApp.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/threads")
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
        return threadService.getThreadById(id).orElse(null);
    }

    @PostMapping
    public ResponseEntity<Thread> createThread(@RequestBody Thread thread) {
        Thread createdThread = threadService.createThread(thread);
        return new ResponseEntity<>(createdThread, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Thread> updateThread(@PathVariable String id, @RequestBody Thread threadDetails) {
        return threadService.updateThread(id, threadDetails).orElse(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Thread> deleteThread(@PathVariable String id) {
        return threadService.deleteThread(id).orElse(null);
    }

    @GetMapping("/author/{authorId}")
    public List<Thread> getThreadsByAuthorId(@PathVariable String authorId) {
        return threadService.getThreadsByAuthorId(authorId);
    }

    @GetMapping("/sorted/upvotes")
    public List<Thread> getThreadsSortedByUpvotes() {
        return threadService.getThreadsSortedByUpvotes();
    }

    @GetMapping("/sorted/downvotes")
    public List<Thread> getThreadsSortedByDownvotes() {
        return threadService.getThreadsSortedByDownvotes();
    }

    @GetMapping("/sorted/createdAt")
    public List<Thread> getThreadsSortedByCreatedAt() {
        return threadService.getThreadsSortedByCreatedAt();
    }

    @GetMapping("/sorted/createdAtAsc")
    public List<Thread> getThreadsSortedByCreatedAtAsc() {
        return threadService.getThreadsSortedByCreatedAtAsc();
    }
}


