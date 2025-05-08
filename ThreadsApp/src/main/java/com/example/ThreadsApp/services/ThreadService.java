package com.wroteit.ThreadApp.service;

import com.wroteit.ThreadApp.model.ThreadPost;
import com.wroteit.ThreadApp.repository.ThreadPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ThreadService {

    private final ThreadPostRepository threadRepo;

    public ThreadPost createThread(ThreadPost thread) {
        thread.setCreatedAt(LocalDateTime.now());
        return threadRepo.save(thread);
    }

    public List<ThreadPost> getAllThreads() {
        return threadRepo.findAll();
    }

    public Optional<ThreadPost> getThreadById(Long id) {
        return threadRepo.findById(id);
    }

    public boolean deleteThread(Long id) {
        if (threadRepo.existsById(id)) {
            threadRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
