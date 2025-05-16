package com.wroteit.UserApp.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class UserResponse {
    private final Long id;
    private final String username;
    private final String email;
    private final String biography;
    private final List<Long> following;
    private final List<Long> blockedUsers;
    private final List<String> subscribedCommunities;
    private final List<String> hiddenCommunities;


    @JsonCreator
    public UserResponse(
            @JsonProperty("id") Long id,
            @JsonProperty("username") String username,
            @JsonProperty("email") String email,
            @JsonProperty("biography") String biography,
            @JsonProperty("following") List<Long> following,
            @JsonProperty("blockedUsers") List<Long> blockedUsers,
            @JsonProperty("subscribedCommunities") List<String> subscribedCommunities,
            @JsonProperty("hiddenCommunities") List<String> hiddenCommunities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.biography = biography;
        this.following = following;
        this.blockedUsers = blockedUsers;
        this.subscribedCommunities = subscribedCommunities;
        this.hiddenCommunities = hiddenCommunities;
    }

    private UserResponse(Builder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.email = builder.email;
        this.biography = builder.biography;
        this.following = builder.following;
        this.blockedUsers = builder.blockedUsers;
        this.subscribedCommunities = builder.subscribedCommunities;
        this.hiddenCommunities = builder.hiddenCommunities;
    }



    public static class Builder {
        private Long id;
        private String username;
        private String email;
        private String biography;
        private List<Long> following;
        private List<Long> blockedUsers;
        private List<String> subscribedCommunities;
        private List<String> hiddenCommunities;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
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

        public Builder following(List<Long> following) {
            this.following = following;
            return this;
        }

        public Builder blockedUsers(List<Long> blockedUsers) {
            this.blockedUsers = blockedUsers;
            return this;
        }

        public Builder subscribedCommunities(List<String> subscribedCommunities) {
            this.subscribedCommunities = subscribedCommunities;
            return this;
        }

        public Builder hiddenCommunities(List<String> hiddenCommunities) {
            this.hiddenCommunities = hiddenCommunities;
            return this;
        }

        public UserResponse build() {
            return new UserResponse(this);
        }
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getBiography() {
        return biography;
    }

    public List<Long> getFollowing() {
        return following;
    }

    public List<Long> getBlockedUsers() {
        return blockedUsers;
    }

    public List<String> getSubscribedCommunities() {
        return subscribedCommunities;
    }

    public List<String> getHiddenCommunities() {
        return hiddenCommunities;
    }

    // equals, hashCode, toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserResponse that = (UserResponse) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(username, that.username) &&
                Objects.equals(email, that.email) &&
                Objects.equals(biography, that.biography) &&
                Objects.equals(following, that.following) &&
                Objects.equals(blockedUsers, that.blockedUsers) &&
                Objects.equals(subscribedCommunities, that.subscribedCommunities) &&
                Objects.equals(hiddenCommunities, that.hiddenCommunities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, biography, following,
                blockedUsers, subscribedCommunities, hiddenCommunities);
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", biography='" + biography + '\'' +
                ", following=" + following +
                ", blockedUsers=" + blockedUsers +
                ", subscribedCommunities=" + subscribedCommunities +
                ", hiddenCommunities=" + hiddenCommunities +
                '}';
    }
}