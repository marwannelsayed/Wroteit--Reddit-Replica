package com.wroteit.ThreadsApp.dto;

public class ThreadRequest {
    public String title;
    public String content;
    public Long authorId;
    public String communityId;

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public String getCommunityId() {
        return communityId;
    }
}
