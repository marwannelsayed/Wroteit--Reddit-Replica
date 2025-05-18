package com.example.threadapp.controller;

import com.example.threadapp.model.Comment;
import com.example.threadapp.model.Comment.EditHistory;
import com.example.threadapp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/threads/{threadId}/comments")
    public List<Comment> getCommentsByThreadId(@PathVariable String threadId) {
        return commentService.getCommentsByThreadId(threadId);
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable String id) {
        return commentService.getCommentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Assuming comments are created in the context of a thread
    @PostMapping("/threads/{threadId}/comments")
    public ResponseEntity<Comment> createComment(@PathVariable String threadId, @RequestBody Comment comment) {
        comment.setThreadId(threadId); // Ensure the comment is associated with the correct thread
        Comment createdComment = commentService.createComment(comment);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable String id, @RequestBody Comment commentDetails) {
        return commentService.updateComment(id, commentDetails, id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/{id}/history")
    public List<EditHistory> getEditHistory(@PathVariable String id) {
        return commentService.getEditHistory(id);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable String id) {
        if (commentService.deleteComment(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}