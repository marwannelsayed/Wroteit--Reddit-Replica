package com.example.ThreadsApp.controller;

import com.example.ThreadsApp.model.Vote;
import com.example.ThreadsApp.service.VoteService;
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
    public Vote castVote(@RequestBody Vote vote) {
        return voteService.castVote(vote);
    }

    @GetMapping("/target/{targetType}/{targetId}")
    public List<Vote> getVotesForTarget(@PathVariable Vote.TargetType targetType, @PathVariable String targetId) {
        return voteService.getVotesForTarget(targetType, targetId);
    }
    @GetMapping("/user/{userId}")
    public List<Vote> getVotesByUser(@PathVariable String userId) {
        return voteService.getVotesByUser(userId);
    }
    @DeleteMapping("/{id}")
    public void deleteVote(@PathVariable String id) {
        voteService.deleteVote(id);
    }


}

