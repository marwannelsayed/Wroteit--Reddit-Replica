package com.wroteit.ThreadsApp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "threads")
public class Thread {
    @Id
    private String id;
    private Long authorId;
    private String communityId;
    private String title;
    private String content;
    private boolean deleted;
    private int upvotes;
    private int downvotes;
    private List<Comment> comments = new ArrayList<>();

    // Constructors
    public Thread() {
        this.upvotes = 0;
        this.downvotes = 0;
        deleted = false;
    }

    public Thread(String title, String content, Long authorId, String communityId) {
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.communityId = communityId;
        this.upvotes = 0;
        this.downvotes = 0;
        deleted = false;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment){
        comments.add(comment);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void addUpvote(){
        upvotes++;
    }

    public void removeUpvote(){
        upvotes--;
    }

    public void addDownvote(){
        downvotes++;
    }

    public void removeDownvote(){
        downvotes--;
    }


}

