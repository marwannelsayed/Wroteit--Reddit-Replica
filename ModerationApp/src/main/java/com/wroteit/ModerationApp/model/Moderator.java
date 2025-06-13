package com.wroteit.ModerationApp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "moderators")
public class Moderator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long moderatorId;

    private Long userId;
    private String communityId;

    public Moderator() {
    }

    public Moderator(Long userId, String communityId) {
        this.userId = userId;
        this.communityId = communityId;
    }

    public Long getModeratorId() {
        return moderatorId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }
}