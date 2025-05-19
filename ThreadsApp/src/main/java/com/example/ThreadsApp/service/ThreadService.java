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

    public String getAllThreads() {
        List<Thread> threads = threadRepository.findAll();
        System.out.println("ThreadService.getAllThreads executed successfully");
        return threads.isEmpty() ? "No threads found" : "Fetched all threads successfully!";
    }

    public List<Thread> getCommunityThreads(Long communityId){
        List<Thread> threads = threadRepository.findByCommunityId(communityId);
        System.out.println("ThreadService.getCommunityThreads executed successfully");
        return threads.isEmpty() ? "No threads found for this community" : "Fetched community threads successfully!";
    }

    public Thread getThreadById(Long id) {
        Thread thread = threadRepository.findById(id).orElse(null);
        System.out.println("ThreadService.getThreadById executed successfully");
        return thread == null ? "Thread not found" : "Thread fetched successfully!";
    }

    public Thread createThread(Thread thread) {
        Thread createdThread = threadRepository.save(thread);
        System.out.println("ThreadService.createThread executed successfully");
        return createdThread != null ? "Thread created successfully!" : "Thread creation failed";
    }

    public Thread updateThread(Long id, String newContent) {
        Thread thread = getThreadById(id);
        if(thread!=null && !thread.isDeleted()){
            thread.setContent(newContent);
            return "Thread updated successfully!";
        }
        return "Thread not found or already deleted";
    }

    public String deleteThread(Long id) {
        if(threadRepository.existsById(id) && !getThreadById(id).isDeleted()){
            DeleteThreadCommand deleteThreadCommand = new DeleteThreadCommand(threadRepository, id);
            deleteThreadCommand.execute();
            System.out.println("ThreadService.deleteThread executed successfully");
            return "Thread deleted successfully!";
        }
        return "Thread not found";
    }

    public String banThread(Long id) {
        if(threadRepository.existsById(id) && !getThreadById(id).isDeleted()){
            BanThreadCommand banThreadCommand = new BanThreadCommand(threadRepository, id);
            banThreadCommand.execute();
            System.out.println("ThreadService.banThread executed successfully");
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
        System.out.println("ThreadService.getThreadsByAuthorId executed successfully");
        return threads.isEmpty() ? "No threads found for this author" : "Fetched threads by author successfully!";
    }

    public List<Thread> getThreadsByCommunityId(Long communityId){
        List<Thread> threads = threadRepository.findByCommunityId(communityId);
        System.out.println("ThreadService.getThreadsByCommunityId executed successfully");
        return threads.isEmpty() ? "No threads found for this community" : "Fetched threads by community successfully!";
    }


}

