package com.example.threadapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "votes")
public class Vote {
    @Id
    private String id;
    private String userId;
    private String targetId; // ID of the thread or comment being voted on
    private TargetType targetType; // Enum: THREAD or COMMENT
    private VoteType voteType; // Enum: UPVOTE or DOWNVOTE
    private LocalDateTime createdAt;

    public enum TargetType {
        THREAD,
        COMMENT
    }

    public enum VoteType {
        UPVOTE,
        DOWNVOTE
    }

    // Constructors
    public Vote() {
        this.createdAt = LocalDateTime.now();
    }

    public Vote(String userId, String targetId, TargetType targetType, VoteType voteType) {
        this.userId = userId;
        this.targetId = targetId;
        this.targetType = targetType;
        this.voteType = voteType;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(TargetType targetType) {
        this.targetType = targetType;
    }

    public VoteType getVoteType() {
        return voteType;
    }

    public void setVoteType(VoteType voteType) {
        this.voteType = voteType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

