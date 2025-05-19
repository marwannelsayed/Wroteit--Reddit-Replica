package com.wroteit.ThreadApp.controller;

import com.wroteit.ThreadApp.model.ThreadPost;
import com.wroteit.ThreadApp.repository.ThreadPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/threads")
@RequiredArgsConstructor
public class ThreadController {

    private final ThreadPostRepository threadRepo;

    @PostMapping
    public ResponseEntity<ThreadPost> createThread(@RequestBody ThreadPost thread) {
        thread.setCreatedAt(LocalDateTime.now());
        ThreadPost saved = threadRepo.save(thread);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<ThreadPost>> getAllThreads() {
        return ResponseEntity.ok(threadRepo.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThreadPost> getThreadById(@PathVariable Long id) {
        return threadRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThread(@PathVariable Long id) {
        if (threadRepo.existsById(id)) {
            threadRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
