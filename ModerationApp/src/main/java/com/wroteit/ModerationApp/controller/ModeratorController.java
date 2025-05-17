package com.wroteit.ModerationApp.controller;

import com.wroteit.ModerationApp.model.Moderator;
import com.wroteit.ModerationApp.service.ModeratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/moderators")
public class ModeratorController {
    private final ModeratorService moderatorService;

    @Autowired
    public ModeratorController(ModeratorService moderatorService) {
        this.moderatorService = moderatorService;
    }

    @PostMapping("/assign")
    public ResponseEntity<String> assignModerator(@RequestBody Map<String, Object> request) {
        Long userId = Long.valueOf(request.get("userId").toString());
        Long communityId = Long.valueOf(request.get("communityId").toString());

        moderatorService.assignModerator(userId, communityId);
        return new ResponseEntity<>("Moderator assigned successfully", HttpStatus.CREATED);
    }

    @GetMapping("/community/{communityId}")
    public ResponseEntity<List<Moderator>> getModeratorsForCommunity(@PathVariable Long communityId) {
        List<Moderator> moderators = moderatorService.getModeratorsForCommunity(communityId);
        return new ResponseEntity<>(moderators, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Moderator>> getCommunitiesModeratedByUser(@PathVariable Long userId) {
        List<Moderator> communities = moderatorService.getCommunitiesModeratedByUser(userId);
        return new ResponseEntity<>(communities, HttpStatus.OK);
    }

    // karim osama
    @PostMapping("/ban")
    public ResponseEntity<String> banUser(@RequestBody Map<String, Object> request) {
        Long userId = Long.valueOf(request.get("userId").toString());
        Long communityId = Long.valueOf(request.get("communityId").toString());
        Long moderatorId = Long.valueOf(request.get("moderatorId").toString());
        
        moderatorService.banUser(userId, communityId, moderatorId);
        return new ResponseEntity<>("User banned successfully", HttpStatus.OK);
    }
    @DeleteMapping("/content/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId, @RequestBody Map<String, Object> request) {
        Long communityId = Long.valueOf(request.get("communityId").toString());
        Long moderatorId = Long.valueOf(request.get("moderatorId").toString());
        
        moderatorService.deletePost(postId, communityId, moderatorId);
        return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
    }
    @GetMapping("/check-moderator")
    public ResponseEntity<Boolean> isModeratorForCommunity(@RequestParam Long userId, @RequestParam Long communityId) {
        boolean isModerator = moderatorService.isModeratorForCommunity(userId, communityId);
        return new ResponseEntity<>(isModerator, HttpStatus.OK);
    }
    //TODO: Integration with ThreadsApp - Add endpoints to fetch thread information
    // Example: GET /moderators/thread/{threadId}/info to get thread info
    
    
    // TODO: Integration with UserApp - Add endpoints to fetch user information
    // Example: GET /moderators/user/{userId}/info to get user info for moderation purposes
}
