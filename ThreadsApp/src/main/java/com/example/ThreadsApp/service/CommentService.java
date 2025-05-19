package com.example.ThreadsApp.service;

import com.example.ThreadsApp.command.BanCommentCommand;
import com.example.ThreadsApp.command.BanThreadCommand;
import com.example.ThreadsApp.command.CreateCommentCommand;
import com.example.ThreadsApp.command.DeleteCommentCommand;
import com.example.ThreadsApp.composite.CommentComponent;
import com.example.ThreadsApp.model.Comment;
import com.example.ThreadsApp.model.Thread;
import com.example.ThreadsApp.repository.CommentRepository;
import com.example.ThreadsApp.repository.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
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

    public List<Comment> getCommentsByThreadId(Long threadId) {
        Thread thread = threadRepository.findById(threadId).orElse(null);
        if(thread!=null){
            return "Comments fetched successfully.";
        }
        return "Thread not found.";
    }

    public List<CommentComponent> getCommentsByParentId(Long parentId){
        Comment parent = commentRepository.findById(parentId).orElse(null);
        if(parent!=null){
            return "Replies fetched successfully.";
        }
        return "Parent comment not found.";
    }

    public Comment getCommentById(Long id) {
        Comment comment = commentRepository.findById(id).orElse(null);
        if(comment != null) {
            return "Comment fetched successfully.";
        }
        return "Comment not found.";
    }

    public Comment createComment(Comment comment) {
        CreateCommentCommand createCommentCommand = new CreateCommentCommand(commentRepository, threadRepository, comment);
        createCommentCommand.execute();
        if(getCommentById(comment.getId()) != null) {
            return "Comment created successfully.";
        }
        return "Failed to create comment.";
    }

    public Comment updateComment(Long id, String updatedComment) {
        Comment comment = commentRepository.findById(id).orElse(null);
        if(comment!=null && !comment.isDeleted()){
            comment.setContent(updatedComment);
            commentRepository.save(comment);
            return "Comment updated successfully.";
        }
        return "Comment not found or is deleted.";
    }


    public String deleteComment(Long id) {
        if(commentRepository.existsById(id) && !getCommentById(id).equals("Comment not found or is deleted.")){
            DeleteCommentCommand deleteCommentCommand = new DeleteCommentCommand(commentRepository, id);
            deleteCommentCommand.execute();
            return "Comment deleted successfully!";
        }
        return "Comment not found or already deleted.";
    }

    public String banComment(Long id) {
        if(commentRepository.existsById(id) && !getCommentById(id).equals("Comment not found or is deleted.")){
            BanCommentCommand banCommentCommand = new BanCommentCommand(commentRepository, id);
            banCommentCommand.execute();
            return "Comment banned successfully!";
        }
        return "Comment not found or already deleted.";
    }


}




