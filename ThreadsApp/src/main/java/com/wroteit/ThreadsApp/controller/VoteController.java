package com.wroteit.ThreadsApp.controller;

import com.wroteit.ThreadsApp.model.Vote;
import com.wroteit.ThreadsApp.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String upvote(@PathVariable(name = "userId") Long userId, @PathVariable(name = "contentId") String contentId) {
        return voteService.upvote(userId, contentId);
    }

    @PostMapping("/{userId}/downvote/{contentId}")
    public String downvote(@PathVariable(name = "userId") Long userId, @PathVariable(name = "contentId") String contentId) {
        return voteService.downvote(userId, contentId);
    }


    @GetMapping("/target/{targetId}")
    public List<Vote> getVotesForTarget( @PathVariable(name = "targetId") String targetId) {
        return voteService.getVotesForTarget(targetId);
    }


    @DeleteMapping("/{id}")
    public void deleteVote(@PathVariable(name = "id") String id) {
        voteService.deleteVote(id);
    }


}

