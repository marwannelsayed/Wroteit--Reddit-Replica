package com.example.ThreadsApp.service;

import com.example.ThreadsApp.command.BanThreadCommand;
import com.example.ThreadsApp.command.DeleteCommentCommand;
import com.example.ThreadsApp.command.DeleteThreadCommand;
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
        List<Thread> threads = threadRepository.findAll();
        System.out.println("Threads fetched successfully");
        return threads;
    }

    public List<Thread> getCommunityThreads(Long communityId) {
        List<Thread> threads = threadRepository.findByCommunityId(communityId);
        System.out.println("Community threads fetched successfully");
        return threads;
    }

    public Thread getThreadById(Long id) {
        Thread thread = threadRepository.findById(id);
        if(thread == null || thread.isDeleted()) {
            System.out.println("Thread not found");
            return null;
        }
        System.out.println("Thread by id fetched successfully");
        return thread;
    }

    public Thread createThread(Thread thread) {
        Thread createdThread = threadRepository.save(thread);
        System.out.println("Thread created successfully");
        return createdThread;
    }

    public Thread updateThread(Long id, String newContent) {
        Thread thread = getThreadById(id);
        if(thread != null && !thread.isDeleted()) {
            thread.setContent(newContent);
            System.out.println("Thread updated successfully");
        }
        return thread;
    }

    public String deleteThread(Long id) {
        if(threadRepository.existsById(id) && !getThreadById(id).isDeleted()){
            DeleteThreadCommand deleteThreadCommand = new DeleteThreadCommand(threadRepository, id);
            deleteThreadCommand.execute();
            return "Thread deleted successfully!";
        }
        return "Thread not found";
    }

    public String banThread(Long id) {
        if(threadRepository.existsById(id) && !getThreadById(id).isDeleted()){
            BanThreadCommand banThreadCommand = new BanThreadCommand(threadRepository, id);
            banThreadCommand.execute();
            return "Thread banned successfully!";
        }
        return "Thread not found";
    }
    

    // // Placeholder for reflection-based logging
    // private void logEditAction(Object entity, String actionType) {
    //     // Implement reflection logic to detect changes and log them
    //     System.out.println("Action: " + actionType + " on entity: " + entity.toString());
    // }

    public List<Thread> getThreadsByAuthorId(Long authorId) {
        List<Thread> threads = threadRepository.findByAuthorId(authorId);
        System.out.println("Threads by author fetched successfully");
        return threads;
    }

    public List<Thread> getThreadsByCommunityId(Long communityId) {
        List<Thread> threads = threadRepository.findByCommunityId(communityId);
        System.out.println("Threads by community fetched successfully");
        return threads;
    }


}

