package com.wroteit.ThreadsApp.dto;

public class CommentRequest {
    public String parentId;
    public Long authorId;
    public String content;


    public String getParentId() {
        return parentId;
    }

    public String getContent() {
        return content;
    }

    public Long getAuthorId() {
        return authorId;
    }


}
