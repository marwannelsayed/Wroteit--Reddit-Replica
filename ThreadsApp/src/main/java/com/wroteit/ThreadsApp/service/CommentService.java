package com.wroteit.ThreadsApp.service;

import com.wroteit.ThreadsApp.command.BanCommentCommand;
import com.wroteit.ThreadsApp.command.CreateCommentCommand;
import com.wroteit.ThreadsApp.command.DeleteCommentCommand;
import com.wroteit.ThreadsApp.composite.CommentComponent;
import com.wroteit.ThreadsApp.model.Comment;
import com.wroteit.ThreadsApp.model.Thread;
import com.wroteit.ThreadsApp.repository.CommentRepository;
import com.wroteit.ThreadsApp.repository.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ThreadRepository threadRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, ThreadRepository threadRepository) {
        this.commentRepository = commentRepository;
        this.threadRepository = threadRepository;
    }

    public List<Comment> getCommentsByThreadId(String threadId) {
        Thread thread = threadRepository.findById(threadId).orElse(null);
        if(thread!=null){
            List<Comment> comments = thread.getComments();
            System.out.println("Comments by threadId fetched successfully!");
            return comments;
        }
        System.out.println("Failed to fetch comments by threadId");
        return null;
    }

    public List<CommentComponent> getCommentsByParentId(String parentId){
        Comment parent = commentRepository.findById(parentId).orElse(null);
        if(parent!=null){
            List<CommentComponent> replies = parent.getReplies();
            System.out.println("Comments by parentId fetched successfully!");
            return replies;
        }
        System.out.println("Failed to fetch comments by parentId");
        return null;
    }

    public Comment getCommentById(String id) {
        Comment comment = commentRepository.findById(id).orElse(null);
        if(comment!=null){
            System.out.println("Comment fetched successfully!");
            return comment;
        }
        System.out.println("Failed to fetch comment by id");
        return null;
    }

    public Comment createComment(Comment comment) {
        CreateCommentCommand createCommentCommand = new CreateCommentCommand(commentRepository, threadRepository, comment);
        createCommentCommand.execute();
        Comment created = getCommentById(comment.getId());
        System.out.println("CommentService.createComment returning: " + created);
        return created;
    }

    public Comment updateComment(String commentId, String updatedComment) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null || comment.isDeleted()) return comment;

        String parentId = comment.getParentId();
        Optional<Thread> threadOpt = threadRepository.findById(parentId);
        if (threadOpt.isPresent()) {
            Thread thread = threadOpt.get();
            updateCommentContent(thread.getComments(), commentId, updatedComment);
            threadRepository.save(thread);
            comment.setContent(updatedComment);
            commentRepository.save(comment);
            return comment;
        } else {
            Comment parentComment = commentRepository.findById(parentId).orElse(null);
            if (parentComment != null) {
                updateCommentContentComposite(parentComment.getReplies(), commentId, updatedComment);
                commentRepository.save(parentComment);
                comment.setContent(updatedComment);
                commentRepository.save(comment);
                return comment;
            }
        }
        return null;
    }

    private void updateCommentContent(List<Comment> comments, String commentId, String updatedContent) {
        for (Comment c : comments) {
            if (c.getId().equals(commentId) && !c.isDeleted()) {
                c.setContent(updatedContent);
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
                    return;
                }
            }
        }
    }


    public String deleteComment(String id) {
        if(commentRepository.existsById(id) && !getCommentById(id).isDeleted()){
            DeleteCommentCommand deleteCommentCommand = new DeleteCommentCommand(commentRepository, threadRepository, id);
            deleteCommentCommand.execute();
            System.out.println("Comment deleted successfully.");
            return "Comment deleted successfully!";
        }
        System.out.println("Comment not found or already deleted.");
        return "Comment not found or already deleted.";
    }

    public String banComment(String id) {
        if(commentRepository.existsById(id) && !getCommentById(id).isDeleted()){
            BanCommentCommand banCommentCommand = new BanCommentCommand(commentRepository, threadRepository, id);
            banCommentCommand.execute();
            System.out.println("Comment banned successfully.");
            return "Comment banned successfully!";
        }
        System.out.println("Comment not found or already deleted.");
        return "Comment not found or already deleted.";
    }


}




