package com.wroteit.ThreadsApp.model;

import com.wroteit.ThreadsApp.composite.CommentComponent;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "comments")
public class Comment implements CommentComponent {
    @Id
    private String id;
    private Long authorId;
    private String parentId;
    private String content;
    private boolean deleted;
    private int upvotes;
    private int downvotes;
    private List<CommentComponent> replies = new ArrayList<>();

    // Constructors
    public Comment() {
        this.upvotes = 0;
        this.downvotes = 0;
        deleted = false;
    }

    public Comment(String parentId, Long authorId, String content) {
        this.authorId = authorId;
        this.parentId = parentId;
        this.content = content;
        this.upvotes = 0;
        this.downvotes = 0;
        deleted = false;
    }


    // Getters and Setters
    public String getId() {
        return id;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
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

    @Override
    public List<CommentComponent> getReplies() {
        return replies;
    }

    @Override
    public void addReply(CommentComponent comment) {
        replies.add(comment);
    }

    public void setContent(String content) {
        this.content = content;
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