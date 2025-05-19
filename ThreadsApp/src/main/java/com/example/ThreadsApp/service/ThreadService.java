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
        return threadRepository.findAll();
    }

    public List<Thread> getCommunityThreads(Long communityId){return threadRepository.findByCommunityId(communityId);}

    public Thread getThreadById(Long id) {
        return threadRepository.findById(id).orElse(null);
    }

    public Thread createThread(Thread thread) {
        return threadRepository.save(thread);
    }

    public Thread updateThread(Long id, String newContent) {
        Thread thread = getThreadById(id);
        if(thread!=null && !thread.isDeleted()){
            thread.setContent(newContent);
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
        return threadRepository.findByAuthorId(authorId);
    }

    public List<Thread> getThreadsByCommunityId(Long communityId){
        return threadRepository.findByCommunityId(communityId);
    }


}

