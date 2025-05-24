package com.wroteit.ThreadsApp.command;

import com.wroteit.ThreadsApp.model.Comment;
import com.wroteit.ThreadsApp.model.Thread;
import com.wroteit.ThreadsApp.repository.CommentRepository;
import com.wroteit.ThreadsApp.repository.ThreadRepository;

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
        String parentId = comment.getParentId();
        if (parentId == null) return;

        // Try thread first
        Optional<Thread> threadOpt = threadRepository.findById(parentId);
        if (threadOpt.isPresent()) {
            Thread thread = threadOpt.get();
            Comment saveComment = commentRepository.save(comment);
            thread.addComment(saveComment);
            threadRepository.save(thread);

            return;
        }

        // Try comment
        Optional<Comment> parentCommentOpt = commentRepository.findById(parentId);
        if (parentCommentOpt.isPresent()) {
            Comment parentComment = parentCommentOpt.get();
            Comment saveComment = commentRepository.save(comment);
            parentComment.addReply(saveComment);
            commentRepository.save(parentComment);
        }
    }
}

