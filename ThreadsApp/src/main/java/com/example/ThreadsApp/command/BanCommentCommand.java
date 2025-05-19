package com.example.ThreadsApp.command;

import com.example.ThreadsApp.model.Comment;
import com.example.ThreadsApp.repository.CommentRepository;

import java.util.Optional;

public class BanCommentCommand implements Command {
    private final CommentRepository commentRepository;
    private final Long commentId;

    public BanCommentCommand(CommentRepository commentRepository, Long commentId) {
        this.commentRepository = commentRepository;
        this.commentId = commentId;
    }

    @Override
    public void execute() {
        Optional<Comment> commentOpt = commentRepository.findById(commentId);
        if (commentOpt.isPresent()) {
            Comment comment = commentOpt.get();
            comment.setContent("[removed]");
            comment.setDeleted(true);
            commentRepository.save(comment);
        }
    }
}
