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
    public ResponseEntity<Thread> getThreadById(@PathVariable String id) {
        return threadService.getThreadById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Thread> createThread(@RequestBody Thread thread) {
        Thread createdThread = threadService.createThread(thread);
        return new ResponseEntity<>(createdThread, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Thread> updateThread(@PathVariable String id, @RequestBody Thread threadDetails) {
        return threadService.updateThread(id, threadDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThread(
        @PathVariable String id,
        @RequestHeader("X-User-Id") String userId,
        @RequestHeader("X-User-Role") String role) {

    if (threadService.deleteThreadWithAuthorization(id, userId, role)) {
        return ResponseEntity.noContent().build();
    } else {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // unauthorized attempt
    }
}

}

