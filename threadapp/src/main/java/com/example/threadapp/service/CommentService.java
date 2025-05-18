package com.example.threadapp.service;

import com.example.threadapp.model.Comment;
import com.example.threadapp.model.Comment.EditHistory;
import com.example.threadapp.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getCommentsByThreadId(String threadId) {
        return commentRepository.findByThreadId(threadId);
    }

    public Optional<Comment> getCommentById(String id) {
        return commentRepository.findById(id);
    }

    public Comment createComment(Comment comment) {
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    public Optional<Comment> updateComment(String id, Comment updatedComment, String editorId) {
        return commentRepository.findById(id)
                .map(existing -> {
                    trackChanges(existing, updatedComment, editorId);
                    return commentRepository.save(existing);
                });
    }
    
public List<EditHistory> getEditHistory(String commentId) {
    return commentRepository.findById(commentId)
            .map(Comment::getEditHistory)
            .orElseThrow();
}

private void trackChanges(Comment existing, Comment updated, String editorId) {
    try {
        Field[] fields = Comment.class.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

         
            if (List.of("id", "createdAt", "updatedAt", "editHistory", "childIds").contains(field.getName())) {
                continue;
            }

            Object oldValue = field.get(existing);
            Object newValue = field.get(updated);

            if (newValue != null && !newValue.equals(oldValue)) {
                existing.getEditHistory().add(new EditHistory(
                        LocalDateTime.now(),
                        field.getName(),
                        oldValue,
                        newValue,
                        editorId
                ));
                field.set(existing, newValue);
            }
        }

        existing.setUpdatedAt(LocalDateTime.now());
    } catch (IllegalAccessException e) {
        throw new RuntimeException("Failed to track changes via reflection", e);
    }
}
public boolean deleteComment(String id) {
    if (commentRepository.existsById(id)) {
        commentRepository.deleteById(id);
        return true;
    }
    return false;
}
}




