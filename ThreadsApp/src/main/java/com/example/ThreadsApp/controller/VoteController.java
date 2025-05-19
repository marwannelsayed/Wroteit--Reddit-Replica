package com.example.ThreadsApp.controller;

import com.example.ThreadsApp.model.Vote;
import com.example.ThreadsApp.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/votes")
public class VoteController {

    private final VoteService voteService;

    @Autowired
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping("/{userId}/upvote/{contentId}")
    public String upvote(@PathVariable Long userId, @PathVariable String contentId) {
        return voteService.upvote(userId, contentId);
    }

    @PostMapping("/{userId}/downvote/{contentId}")
    public String downvote(@PathVariable Long userId, @PathVariable String contentId) {
        return voteService.downvote(userId, contentId);
    }


    @GetMapping("/target/{targetId}")
    public List<Vote> getVotesForTarget( @PathVariable String targetId) {
        return voteService.getVotesForTarget(targetId);
    }


    @DeleteMapping("/{id}")
    public void deleteVote(@PathVariable String id) {
        voteService.deleteVote(id);
    }


}

