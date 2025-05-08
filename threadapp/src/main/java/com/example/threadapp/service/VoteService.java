package com.example.threadapp.service;

import com.example.threadapp.model.Thread;
import com.example.threadapp.model.Vote;
import com.example.threadapp.repository.ThreadRepository;
import com.example.threadapp.repository.VoteRepository;
import com.example.threadapp.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final ThreadRepository threadRepository; // To update vote counts on threads
    private final CommentRepository commentRepository; // To update vote counts on comments
    // private final RabbitMQService rabbitMQService; // For sending notifications

    @Autowired
    public VoteService(VoteRepository voteRepository, ThreadRepository threadRepository, CommentRepository commentRepository) {
        this.voteRepository = voteRepository;
        this.threadRepository = threadRepository;
        this.commentRepository = commentRepository;
        // this.rabbitMQService = rabbitMQService;
    }

    public Vote castVote(Vote vote) {
        // Basic validation: Ensure user hasn't voted on this target already (or allow changing vote)
        // This is a simplified version. A more robust implementation would check existing votes by userId and targetId.

        vote.setCreatedAt(LocalDateTime.now());
        Vote savedVote = voteRepository.save(vote);

        // Update vote counts on the target entity (Thread or Comment)
        updateTargetVoteCount(vote.getTargetId(), vote.getTargetType(), vote.getVoteType());

        // Send notification via RabbitMQ (placeholder)
        // String message = String.format("User %s %s your %s.", vote.getUserId(), vote.getVoteType().toString().toLowerCase(), vote.getTargetType().toString().toLowerCase());
        // rabbitMQService.sendNotification(message);
        // logVoteAction(savedVote);

        return savedVote;
    }

    private void updateTargetVoteCount(String targetId, Vote.TargetType targetType, Vote.VoteType voteType) {
        if (targetType == Vote.TargetType.THREAD) {
            threadRepository.findById(targetId).ifPresent(thread -> {
                if (voteType == Vote.VoteType.UPVOTE) {
                    thread.setUpvotes(thread.getUpvotes() + 1);
                } else if (voteType == Vote.VoteType.DOWNVOTE) {
                    thread.setDownvotes(thread.getDownvotes() + 1);
                }
                threadRepository.save(thread);
            });
        } else if (targetType == Vote.TargetType.COMMENT) {
            commentRepository.findById(targetId).ifPresent(comment -> {
                if (voteType == Vote.VoteType.UPVOTE) {
                    comment.setUpvotes(comment.getUpvotes() + 1);
                } else if (voteType == Vote.VoteType.DOWNVOTE) {
                    comment.setDownvotes(comment.getDownvotes() + 1);
                }
                commentRepository.save(comment);
            });
        }
    }

    public List<Vote> getVotesForTarget(String targetId, Vote.TargetType targetType) {
        return voteRepository.findByTargetIdAndTargetType(targetId, targetType.name());
    }

    // Placeholder for reflection-based logging of vote actions
    private void logVoteAction(Vote vote) {
        // Implement reflection logic to log vote details
        System.out.println("Vote cast: " + vote.toString());
    }
}

