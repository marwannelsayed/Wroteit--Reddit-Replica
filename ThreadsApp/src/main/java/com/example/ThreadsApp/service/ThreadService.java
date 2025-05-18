package com.example.ThreadsApp.service;

import com.example.ThreadsApp.model.Thread;
import com.example.ThreadsApp.repository.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ThreadService {

    private final ThreadRepository threadRepository;

    @Autowired
    public ThreadService(ThreadRepository threadRepository) {
        this.threadRepository = threadRepository;
    }

    public List<Thread> getAllThreads() {
        return threadRepository.findAll();
    }

    public Optional<Thread> getThreadById(String id) {
        return threadRepository.findById(id);
    }

    public Thread createThread(Thread thread) {
        // Additional logic for creation, e.g., setting authorId if not set
        // For now, assuming authorId is set in the controller or from security context
        thread.setCreatedAt(LocalDateTime.now());
        thread.setUpdatedAt(LocalDateTime.now());
        return threadRepository.save(thread);
    }

    public Optional<Thread> updateThread(String id, Thread threadDetails) {
        return threadRepository.findById(id)
                .map(existingThread -> {
                    existingThread.setTitle(threadDetails.getTitle());
                    existingThread.setContent(threadDetails.getContent());
                    existingThread.setUpdatedAt(LocalDateTime.now());
                    // Implement reflection for logging edits here if required
                    return threadRepository.save(existingThread);
                });
    }

    public boolean deleteThreadWithAuthorization(String id, String userId, String role) {
        Optional<Thread> optionalThread = threadRepository.findById(id);
        if (optionalThread.isEmpty()) return false;
    
        Thread thread = optionalThread.get();
        boolean isModerator = "MODERATOR".equalsIgnoreCase(role);
        boolean isOwner = thread.getAuthorId().equals(userId);
    
        if (isModerator || isOwner) {
            threadRepository.deleteById(id);
            return true;
        }
    
        return false; // forbidden
    }
    

    // Placeholder for reflection-based logging
    private void logEditAction(Object entity, String actionType) {
        // Implement reflection logic to detect changes and log them
        System.out.println("Action: " + actionType + " on entity: " + entity.toString());
    }
}

