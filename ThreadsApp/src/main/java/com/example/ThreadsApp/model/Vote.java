package com.example.ThreadsApp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "votes")
public class Vote {
    @Id
    private Long id;
    private Long userId;
    private Long targetId; // ID of the thread or comment being voted on
    private TargetType targetType; // Enum: THREAD or COMMENT
    private VoteType voteType; // Enum: UPVOTE or DOWNVOTE

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
    }

    public Vote(Long userId, Long targetId, TargetType targetType, VoteType voteType) {
        this.userId = userId;
        this.targetId = targetId;
        this.targetType = targetType;
        this.voteType = voteType;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
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


}

