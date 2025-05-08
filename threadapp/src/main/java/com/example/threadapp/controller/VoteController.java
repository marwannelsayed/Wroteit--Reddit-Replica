package com.example.threadapp.controller;

import com.example.threadapp.model.Vote;
import com.example.threadapp.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/votes")
public class VoteController {

    private final VoteService voteService;

    @Autowired
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping
    public ResponseEntity<Vote> castVote(@RequestBody Vote vote) {
        // Basic validation: Ensure targetId, targetType, userId, and voteType are present
        if (vote.getUserId() == null || vote.getTargetId() == null || vote.getTargetType() == null || vote.getVoteType() == null) {
            return ResponseEntity.badRequest().build(); // Or a more specific error response
        }
        Vote castedVote = voteService.castVote(vote);
        return new ResponseEntity<>(castedVote, HttpStatus.CREATED);
    }

    @GetMapping("/target/{targetType}/{targetId}")
    public ResponseEntity<List<Vote>> getVotesForTarget(@PathVariable Vote.TargetType targetType, @PathVariable String targetId) {
        List<Vote> votes = voteService.getVotesForTarget(targetId, targetType);
        if (votes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(votes);
    }
}

