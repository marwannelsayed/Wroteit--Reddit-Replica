package com.example.ThreadsApp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "comments")
public class Comment {
    @Id
    private String id;
    private String threadId;
    private String authorId;
    private String parentId; // For nested comments
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<EditHistory> editHistory = new ArrayList<>();
    private int upvotes;
    private int downvotes;

    // Constructors
    public Comment() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.upvotes = 0;
        this.downvotes = 0;
    }

    public Comment(String threadId, String authorId, String content) {
        this.threadId = threadId;
        this.authorId = authorId;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.upvotes = 0;
        this.downvotes = 0;
    }
    public record EditHistory(
            LocalDateTime timestamp,
            String fieldName,
            Object oldValue,
            Object newValue,
            String editedBy
    ) {}

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }
    public List<EditHistory> getEditHistory() {
        return editHistory;
    }
    
    public void setEditHistory(List<EditHistory> editHistory) {
        this.editHistory = editHistory;
    }
    

}