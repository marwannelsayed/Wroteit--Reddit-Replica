package com.example.ThreadsApp.command;

import com.example.ThreadsApp.model.Comment;
import com.example.ThreadsApp.model.Thread;
import com.example.ThreadsApp.repository.CommentRepository;
import com.example.ThreadsApp.repository.ThreadRepository;

import java.util.Optional;

public class CreateCommentCommand implements Command {
    private final CommentRepository commentRepository;
    private final ThreadRepository threadRepository;
    private final Comment comment;

    public CreateCommentCommand(CommentRepository commentRepository, ThreadRepository threadRepository, Comment comment) {
        this.commentRepository = commentRepository;
        this.threadRepository = threadRepository;
        this.comment = comment;
    }

    @Override
    public void execute() {
        Long parentId = comment.getParentId();
        if (parentId == null) return;

        // Try thread first
        Optional<Thread> threadOpt = threadRepository.findById(parentId);
        if (threadOpt.isPresent()) {
            Thread thread = threadOpt.get();
            thread.addComment(comment);
            threadRepository.save(thread);
            commentRepository.save(comment);
            return;
        }

        // Try comment
        Optional<Comment> parentCommentOpt = commentRepository.findById(parentId);
        if (parentCommentOpt.isPresent()) {
            Comment parentComment = parentCommentOpt.get();
            parentComment.addReply(comment);
            commentRepository.save(comment);
            commentRepository.save(parentComment);
        }
    }
}

