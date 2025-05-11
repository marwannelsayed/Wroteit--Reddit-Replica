package com.wroteit.UserApp.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String email;
    private String biography;

    @ElementCollection
    private List<String> subscribedCommunities = new ArrayList<>();

    @ElementCollection
    private List<Long> followers = new ArrayList<>();

    @ElementCollection
    private List<Long> following = new ArrayList<>();

    @ElementCollection
    private List<Long> blockedUsers = new ArrayList<>();

    @ElementCollection
    private List<String> hiddenCommunities = new ArrayList<>();

    // Private constructor for Builder
    private User(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.email = builder.email;
        this.biography = builder.biography;
        this.subscribedCommunities = builder.subscribedCommunities;
        this.followers = builder.followers;
        this.following = builder.following;
        this.blockedUsers = builder.blockedUsers;
        this.hiddenCommunities = builder.hiddenCommunities;
    }

    // No-arg constructor for JPA
    protected User() {}

    // Builder class
    public static class Builder {
        private String username;
        private String password;
        private String email;
        private String biography;
        private List<String> subscribedCommunities = new ArrayList<>();
        private List<Long> followers = new ArrayList<>();
        private List<Long> following = new ArrayList<>();
        private List<Long> blockedUsers = new ArrayList<>();
        private List<String> hiddenCommunities = new ArrayList<>();

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder biography(String biography) {
            this.biography = biography;
            return this;
        }

        public Builder subscribedCommunities(List<String> subscribedCommunities) {
            this.subscribedCommunities = subscribedCommunities;
            return this;
        }

        public Builder followers(List<Long> followers) {
            this.followers = followers;
            return this;
        }

        public Builder following(List<Long> following) {
            this.following = following;
            return this;
        }

        public Builder blockedUsers(List<Long> blockedUsers) {
            this.blockedUsers = blockedUsers;
            return this;
        }

        public Builder hiddenCommunities(List<String> hiddenCommunities) {
            this.hiddenCommunities = hiddenCommunities;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    // ---------------- GETTERS ----------------

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getBiography() {
        return biography;
    }

    public List<String> getSubscribedCommunities() {
        return subscribedCommunities;
    }

    public List<Long> getFollowers() {
        return followers;
    }

    public List<Long> getFollowing() {
        return following;
    }

    public List<Long> getBlockedUsers() {
        return blockedUsers;
    }

    public List<String> getHiddenCommunities() {
        return hiddenCommunities;
    }

    // ---------------- SETTERS ----------------

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public void setSubscribedCommunities(List<String> subscribedCommunities) {
        this.subscribedCommunities = subscribedCommunities;
    }

    public void setFollowers(List<Long> followers) {
        this.followers = followers;
    }

    public void setFollowing(List<Long> following) {
        this.following = following;
    }

    public void setBlockedUsers(List<Long> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    public void setHiddenCommunities(List<String> hiddenCommunities) {
        this.hiddenCommunities = hiddenCommunities;
    }
}
