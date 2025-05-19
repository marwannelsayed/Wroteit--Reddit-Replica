package com.example.ThreadsApp.service;

import com.example.ThreadsApp.model.Thread;
import com.example.ThreadsApp.model.Vote;
import com.example.ThreadsApp.repository.ThreadRepository;
import com.example.ThreadsApp.repository.VoteRepository;
import com.example.ThreadsApp.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final ThreadRepository threadRepository; // To update vote counts on threads
    private final CommentRepository commentRepository; // To update vote counts on comments
    private final RabbitMQService rabbitMQService;


    @Autowired
    public VoteService(VoteRepository voteRepository,
                       ThreadRepository threadRepository,
                       CommentRepository commentRepository,
                       RabbitMQService rabbitMQService) {
        this.voteRepository = voteRepository;
        this.threadRepository = threadRepository;
        this.commentRepository = commentRepository;
        this.rabbitMQService = rabbitMQService;
    }




    public Vote castVote(Vote vote) {
        Optional<Vote> existingVoteOpt = voteRepository.findAll().stream()
                .filter(v -> v.getUserId().equals(vote.getUserId()) &&
                        v.getTargetId().equals(vote.getTargetId()) &&
                        v.getTargetType() == vote.getTargetType())
                .findFirst();

        Vote savedVote;

        if (existingVoteOpt.isPresent()) {
            Vote existingVote = existingVoteOpt.get();

            // Only update vote count if the vote type has changed
            if (!existingVote.getVoteType().equals(vote.getVoteType())) {
                // Reverse old vote first (optional logic)
                if (existingVote.getVoteType() == Vote.VoteType.UPVOTE) {
                    decrementVoteCount(existingVote.getTargetId(), existingVote.getTargetType(), Vote.VoteType.UPVOTE);
                } else {
                    decrementVoteCount(existingVote.getTargetId(), existingVote.getTargetType(), Vote.VoteType.DOWNVOTE);
                }

                logVoteChanges(existingVote, vote);
                existingVote.setVoteType(vote.getVoteType());
                existingVote.setCreatedAt(LocalDateTime.now());

                savedVote = voteRepository.save(existingVote);

                // Apply new vote
                updateTargetVoteCount(savedVote.getTargetId(), savedVote.getTargetType(), savedVote.getVoteType());
            } else {
                // No change in vote type → no update
                savedVote = existingVote;
            }

        } else {
            vote.setCreatedAt(LocalDateTime.now());
            savedVote = voteRepository.save(vote);
            updateTargetVoteCount(savedVote.getTargetId(), savedVote.getTargetType(), savedVote.getVoteType());
        }


        String message = String.format("User %s %s your %s (%s)",
                savedVote.getUserId(),
                savedVote.getVoteType().toString().toLowerCase(),
                savedVote.getTargetType().toString().toLowerCase(),
                savedVote.getTargetId());

        rabbitMQService.sendNotification(message);

        return savedVote;
    }

    private void decrementVoteCount(String targetId, Vote.TargetType targetType, Vote.VoteType voteType) {
        if (targetType == Vote.TargetType.THREAD) {
            threadRepository.findById(targetId).ifPresent(thread -> {
                if (voteType == Vote.VoteType.UPVOTE) {
                    thread.setUpvotes(Math.max(0, thread.getUpvotes() - 1));
                } else {
                    thread.setDownvotes(Math.max(0, thread.getDownvotes() - 1));
                }
                threadRepository.save(thread);
            });
        } else if (targetType == Vote.TargetType.COMMENT) {
            commentRepository.findById(targetId).ifPresent(comment -> {
                if (voteType == Vote.VoteType.UPVOTE) {
                    comment.setUpvotes(Math.max(0, comment.getUpvotes() - 1));
                } else {
                    comment.setDownvotes(Math.max(0, comment.getDownvotes() - 1));
                }
                commentRepository.save(comment);
            });
        }
    }

    public List<Vote> getVotesByUser(String userId) {
        return voteRepository.findAll().stream()
                .filter(v -> v.getUserId().equals(userId))
                .toList();
    }

    public boolean deleteVote(String id) {
        if (voteRepository.existsById(id)) {
            voteRepository.deleteById(id);
            return true;
        }
        return false;
    }



    private void logVoteChanges(Vote oldVote, Vote newVote) {
        try {
            Field[] fields = Vote.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object oldValue = field.get(oldVote);
                Object newValue = field.get(newVote);

                if (newValue != null && !newValue.equals(oldValue)) {
                    System.out.printf("Field '%s' changed from '%s' to '%s'%n",
                            field.getName(), oldValue, newValue);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to log vote changes", e);
        }
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

