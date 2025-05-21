package com.wroteit.ThreadsApp.service;

import com.wroteit.ThreadsApp.command.BanThreadCommand;
import com.wroteit.ThreadsApp.command.DeleteThreadCommand;
import com.wroteit.ThreadsApp.model.Comment;
import com.wroteit.ThreadsApp.model.Thread;
import com.wroteit.ThreadsApp.repository.CommentRepository;
import com.wroteit.ThreadsApp.repository.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThreadService {

    private final ThreadRepository threadRepository;
    private final CommentRepository commentRepository;


    @Autowired
    public ThreadService(ThreadRepository threadRepository, CommentRepository commentRepository) {
        this.threadRepository = threadRepository;
        this.commentRepository = commentRepository;
    }

    public List<Thread> getAllThreads() {
        List<Thread> threads = threadRepository.findAll();
        System.out.println("Threads fetched successfully");
        return threads;
    }


    public Thread getThreadById(String id) {
        Thread thread = threadRepository.findById(id).orElse(null);
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

    public Thread updateThread(String id, String newContent) {
        Thread thread = getThreadById(id);
        if(thread != null && !thread.isDeleted()) {
            thread.setContent(newContent);
            System.out.println("Thread updated successfully");
            threadRepository.save(thread);
            return thread;
        }
        System.out.println("Thread not found or already deleted");
        return thread;
    }

    public String deleteThread(String id) {
        if(threadRepository.existsById(id) && !getThreadById(id).isDeleted()){
            DeleteThreadCommand deleteThreadCommand = new DeleteThreadCommand(threadRepository, id);
            deleteThreadCommand.execute();
            System.out.println("Thread deleted successfully");
            return "Thread deleted successfully!";
        }
        System.out.println("Thread not found");
        return "Thread not found";
    }

    public String banThread(String id) {
        if(threadRepository.existsById(id) && !getThreadById(id).isDeleted()){
            BanThreadCommand banThreadCommand = new BanThreadCommand(threadRepository, id);
            banThreadCommand.execute();
            System.out.println("Thread banned successfully!");
            return "Thread banned successfully!";
        }
        System.out.println("Thread not found");
        return "Thread not found";
    }
    

    // // Placeholder for reflection-based logging
    // private void logEditAction(Object entity, String actionType) {
    //     // Implement reflection logic to detect changes and log them
    //     System.out.println("Action: " + actionType + " on entity: " + entity.toString());
    // }

    public List<Thread> getThreadsByAuthorId(Long authorId) {
        List<Thread> threads = threadRepository.findByAuthorId(authorId);
        if(threads.isEmpty()){
            System.out.println("No threads by author yet!");
        }
        else{
            System.out.println("Threads by author fetched successfully");
        }
        return threads;
    }

    public List<Thread> getThreadsByCommunityId(String communityId) {
        List<Thread> threads = threadRepository.findByCommunityId(communityId);
        if(threads.isEmpty()){
            System.out.println("No threads in community yet!");
        }
        else{
            System.out.println("Threads in community fetched successfully");
        }
        return threads;
    }


    public boolean threadExists(String parentId) {
        return threadRepository.existsById(parentId);
    }

    public Long getAuthorIdByCommentParentId(String parentId) {
        Thread parentThread = getThreadById(parentId);
        if(parentThread != null) return parentThread.getAuthorId();
        Comment parentComment = commentRepository.findById(parentId).orElse(null);
        if(parentComment != null) return parentComment.getAuthorId();
        return null;
    }
}

