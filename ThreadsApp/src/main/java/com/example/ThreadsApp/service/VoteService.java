package com.example.ThreadsApp.service;

import com.example.ThreadsApp.command.DownvoteCommand;
import com.example.ThreadsApp.command.UpvoteCommand;
import com.example.ThreadsApp.model.Comment;
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

    public String upvote(Long userId, Long contentId){
        UpvoteCommand upvoteCommand = new UpvoteCommand(userId, contentId, voteRepository, threadRepository, commentRepository);
        String result = upvoteCommand.execute();
        System.out.println("Upvote successful");
        return result;
    }


    public String downvote(Long userId, Long contentId){
        DownvoteCommand downvoteCommand = new DownvoteCommand(userId, contentId, voteRepository, threadRepository, commentRepository);
        String result = downvoteCommand.execute();
        System.out.println("Downvote successful");
        return result;
    }







    public List<Vote> getVotesByUser(String userId) {
        List<Vote> votes = voteRepository.findAll().stream()
                .filter(v -> v.getUserId().equals(userId))
                .toList();
        System.out.println("Votes by user fetched successfully");
        return votes;
    }

    public String deleteVote(String id) {
        if (voteRepository.existsById(id)) {
            voteRepository.deleteById(id);
            return "Vote deleted successfully!";
        }
        return "Vote not found!";
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
        List<Vote> votes = voteRepository.findByTargetIdAndTargetType(targetId, targetType.name());
        System.out.println("votes fetched for target successfully");
        return votes;
    }

    // Placeholder for reflection-based logging of vote actions
    private void logVoteAction(Vote vote) {
        // Implement reflection logic to log vote details
        System.out.println("Vote cast: " + vote.toString());
    }
}

