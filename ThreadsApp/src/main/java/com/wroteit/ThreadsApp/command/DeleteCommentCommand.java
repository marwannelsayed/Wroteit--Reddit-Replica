package com.wroteit.ThreadsApp.command;

import com.wroteit.ThreadsApp.composite.CommentComponent;
import com.wroteit.ThreadsApp.model.Comment;
import com.wroteit.ThreadsApp.model.Thread;
import com.wroteit.ThreadsApp.repository.CommentRepository;
import com.wroteit.ThreadsApp.repository.ThreadRepository;

import java.util.List;
import java.util.Optional;

public class DeleteCommentCommand implements Command {
    private final CommentRepository commentRepository;
    private final ThreadRepository threadRepository;
    private final String commentId;

    public DeleteCommentCommand(CommentRepository commentRepository, ThreadRepository threadRepository, String commentId) {
        this.commentRepository = commentRepository;
        this.threadRepository = threadRepository;
        this.commentId = commentId;
    }

    @Override
    public void execute() {
        Optional<Comment> commentOpt = commentRepository.findById(commentId);
        if (commentOpt.isPresent()) {
            Comment comment = commentOpt.get();
            String parentId = comment.getParentId();

            // Mark comment as deleted
            comment.setContent("[deleted]");
            comment.setDeleted(true);
            commentRepository.save(comment);

            // Update comment in parent Thread or Comment replies
            Optional<Thread> threadOpt = threadRepository.findById(parentId);
            if (threadOpt.isPresent()) {
                Thread thread = threadOpt.get();
                updateCommentContent(thread.getComments(), commentId, "[deleted]");
                threadRepository.save(thread);
            } else {
                Optional<Comment> parentCommentOpt = commentRepository.findById(parentId);
                if (parentCommentOpt.isPresent()) {
                    Comment parentComment = parentCommentOpt.get();
                    updateCommentContentComposite(parentComment.getReplies(), commentId, "[deleted]");
                    commentRepository.save(parentComment);
                }
            }
        }
    }

    private void updateCommentContent(List<Comment> comments, String commentId, String updatedContent) {
        for (Comment c : comments) {
            if (c.getId().equals(commentId) && !c.isDeleted()) {
                c.setContent(updatedContent);
                c.setDeleted(true);
                return;
            }
        }
    }

    private void updateCommentContentComposite(List<CommentComponent> comments, String commentId, String updatedContent) {
        for (CommentComponent cc : comments) {
            if (cc instanceof Comment) {
                Comment c = (Comment) cc;
                if (c.getId().equals(commentId) && !c.isDeleted()) {
                    c.setContent(updatedContent);
                    c.setDeleted(true);
                    return;
                }
            }
        }
    }

}
