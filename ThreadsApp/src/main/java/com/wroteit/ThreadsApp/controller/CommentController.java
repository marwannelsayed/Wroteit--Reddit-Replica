package com.wroteit.ThreadsApp.controller;

import com.wroteit.ThreadsApp.dto.CommentRequest;
import com.wroteit.ThreadsApp.model.Comment;
import com.wroteit.ThreadsApp.service.CommentService;
import com.wroteit.ThreadsApp.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final ThreadService threadService;
    RestTemplate restTemplate;
    String baseUrl;

    @Autowired
    public CommentController(CommentService commentService, ThreadService threadService) {
        this.commentService = commentService;
        this.threadService = threadService;
        restTemplate = new RestTemplate();
        baseUrl = "http://gatewayapp:8080";
    }

    @GetMapping("/threads/{threadId}/comments")
    public List<Comment> getCommentsByThreadId(@PathVariable(name = "threadId") String threadId) {
        return commentService.getCommentsByThreadId(threadId);
    }

    @GetMapping("/comments/{id}")
    public Comment getCommentById(@PathVariable(name = "id") String id) {
        return commentService.getCommentById(id);
    }

    // Assuming comments are created in the context of a thread
    @PostMapping
    public Comment createComment(@RequestBody CommentRequest comment) {
        Comment created = commentService.createComment(comment);

        String parentId = comment.getParentId();
        String url;

        if (threadService.threadExists(parentId)) {
            url = "/notifications/comment";
        } else {
            url = "/notifications/reply";
        }

        Long recipientId = threadService.getAuthorIdByCommentParentId(parentId);
        if (recipientId != null) {
            Map<String, Object> body = new HashMap<>();
            body.put("recipientId", recipientId);
            body.put("message", "You have a new reply.");
            restTemplate.postForObject(baseUrl + url, body, Void.class);
        }

        return created;
    }


    @PutMapping("/comments/{id}")
    public Comment updateComment(@PathVariable(name = "id") String id, @RequestBody String commentDetails) {
        return commentService.updateComment(id, commentDetails);
    }


    @DeleteMapping("/comments/{id}")
    public String deleteComment(@PathVariable(name = "id") String id) {
        return commentService.deleteComment(id);
    }

    @DeleteMapping("/ban/{id}")
    public String banComment(@PathVariable(name = "id") String id) {
        return commentService.banComment(id);
    }
}