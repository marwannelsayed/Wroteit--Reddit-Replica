package com.example.ThreadsApp.command;

import com.example.ThreadsApp.model.Thread;
import com.example.ThreadsApp.repository.ThreadRepository;

import java.util.Optional;

public class BanThreadCommand implements Command {
    private final ThreadRepository threadRepository;
    private final Long commentId;

    public BanThreadCommand(ThreadRepository threadRepository, Long commentId) {
        this.threadRepository = threadRepository;
        this.commentId = commentId;
    }

    @Override
    public void execute() {
        Optional<Thread> threadOpt = threadRepository.findById(commentId);
        if (threadOpt.isPresent()) {
            Thread thread = threadOpt.get();
            thread.setContent("[removed]");
            thread.setDeleted(true);
            threadRepository.save(thread);
        }
    }
}
