package com.example.ThreadsApp.command;

import com.example.ThreadsApp.model.Comment;
import com.example.ThreadsApp.model.Thread;
import com.example.ThreadsApp.repository.CommentRepository;
import com.example.ThreadsApp.repository.ThreadRepository;

import java.util.Optional;

public class DeleteThreadCommand implements Command {
    private final ThreadRepository threadRepository;
    private final String threadId;

    public DeleteThreadCommand(ThreadRepository threadRepository, String threadId) {
        this.threadRepository = threadRepository;
        this.threadId = threadId;
    }

    @Override
    public void execute() {
        Optional<Thread> threadOpt = threadRepository.findById(threadId);
        if (threadOpt.isPresent()) {
            Thread thread = threadOpt.get();
            thread.setContent("[deleted]");
            thread.setDeleted(true);
            threadRepository.save(thread);
        }
    }
}
