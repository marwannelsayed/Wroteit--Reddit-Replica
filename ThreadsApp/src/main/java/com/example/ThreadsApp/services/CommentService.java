package com.wroteit.ThreadApp.service;

import com.wroteit.ThreadApp.model.Comment;
import com.wroteit.ThreadApp.model.ThreadPost;
import com.wroteit.ThreadApp.repository.CommentRepository;
import com.wroteit.ThreadApp.repository.ThreadPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepo;
    private final ThreadPostRepository threadRepo;

    public Optional<Comment> addCommentToThread(Long threadId, Comment comment) {
        return threadRepo.findById(threadId).map(thread -> {
            comment.setThread(thread);
            comment.setCreatedAt(LocalDateTime.now());
            return commentRepo.save(comment);
        });
    }

    public Optional<Comment> replyToComment(Long parentId, Comment comment) {
        return commentRepo.findById(parentId).map(parent -> {
            comment.setParent(parent);
            comment.setThread(parent.getThread());
            comment.setCreatedAt(LocalDateTime.now());
            return commentRepo.save(comment);
        });
    }

    public List<Comment> getCommentsForThread(Long threadId) {
        return commentRepo.findByThreadId(threadId);
    }
}
