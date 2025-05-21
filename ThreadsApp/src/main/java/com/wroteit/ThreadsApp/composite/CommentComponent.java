package com.wroteit.ThreadsApp.composite;

import java.util.List;

public interface CommentComponent {
    String getId();
    Long getAuthorId();
    String getContent();
    List<CommentComponent> getReplies();
    void addReply(CommentComponent comment);
}

