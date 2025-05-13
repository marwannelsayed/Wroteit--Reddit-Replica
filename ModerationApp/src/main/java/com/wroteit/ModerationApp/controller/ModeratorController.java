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
}