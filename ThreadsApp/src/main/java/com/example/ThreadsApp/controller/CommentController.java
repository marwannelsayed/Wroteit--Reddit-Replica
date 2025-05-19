package com.example.ThreadsApp.controller;

import com.example.ThreadsApp.model.Comment;
import com.example.ThreadsApp.model.Comment.EditHistory;
import com.example.ThreadsApp.service.CommentService;
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
    public List<Comment> getCommentsByThreadId(@PathVariable Long threadId) {
        return commentService.getCommentsByThreadId(threadId);
    }

    @GetMapping("/comments/{id}")
    public Comment getCommentById(@PathVariable Long id) {
        return commentService.getCommentById(id);
    }

    // Assuming comments are created in the context of a thread
    @PostMapping("/threads/{threadId}/comments")
    public Comment createComment(@RequestBody Comment comment) {
        return commentService.createComment(comment);
    }

    @PutMapping("/comments/{id}")
    public Comment updateComment(@PathVariable Long id, @RequestBody String commentDetails) {
        return commentService.updateComment(id, commentDetails);
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