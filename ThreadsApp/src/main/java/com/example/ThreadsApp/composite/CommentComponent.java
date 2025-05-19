package com.example.ThreadsApp.composite;

import java.util.List;

public interface CommentComponent {
    Long getId();
    Long getAuthorId();
    String getContent();
    List<CommentComponent> getReplies();
    void addReply(CommentComponent comment);
}

