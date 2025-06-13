package com.wroteit.ThreadsApp.service;

import com.wroteit.ThreadsApp.command.DownvoteCommand;
import com.wroteit.ThreadsApp.command.UpvoteCommand;
import com.wroteit.ThreadsApp.model.Vote;
import com.wroteit.ThreadsApp.repository.ThreadRepository;
import com.wroteit.ThreadsApp.repository.VoteRepository;
import com.wroteit.ThreadsApp.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public String upvote(Long userId, String contentId){
        UpvoteCommand upvoteCommand = new UpvoteCommand(userId, contentId, voteRepository, threadRepository, commentRepository);
        String result = upvoteCommand.execute();
        System.out.println(result);
        return result;
    }


    public String downvote(Long userId, String contentId){
        DownvoteCommand downvoteCommand = new DownvoteCommand(userId, contentId, voteRepository, threadRepository, commentRepository);
        String result = downvoteCommand.execute();
        System.out.println(result);
        return result;
    }


    public String deleteVote(String id) {
        if (voteRepository.existsById(id)) {
            voteRepository.deleteById(id);
            return "Vote deleted successfully!";
        }
        return "Vote not found!";
    }


    public List<Vote> getVotesForTarget(String targetId) {
        List<Vote> votes = voteRepository.findByTargetId(targetId);
        System.out.println("votes fetched for target successfully");
        return votes;
    }


}

