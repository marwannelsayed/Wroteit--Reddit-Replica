package com.wroteit.CommunitiesApp.controller;
// src/main/java/com/wroteit/CommunitiesApp/controller/CommunityController.java

import com.wroteit.CommunitiesApp.model.Community;
import com.wroteit.CommunitiesApp.service.CommunityService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/communities")
public class CommunityController {
    private final CommunityService service;

    public CommunityController(CommunityService service) {
        this.service = service;
    }
    // TODO: When ModeratorApp is ready, inject ModeratorClient here
    // private final ModeratorClient moderatorClient;
    //
    // public CommunityController(CommunityService service, ModeratorClient moderatorClient) {
    //     this.service = service;
    //     this.moderatorClient = moderatorClient;
    // }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Community createCommunity(
            @RequestBody CreateCommunityRequest req,
            @RequestParam Long userId) {
        // TODO: After ModeratorApp is live, also call moderatorClient.addModerator(communityId, userId);
        //           so the creator becomes a moderator by default.
        Community c = new Community();
        c.setName(req.getName());
        c.setDescription(req.getDescription());
        if (req.getTags() != null) c.setTags(req.getTags());
        return service.createCommunity(c, userId);
    }

    @GetMapping("/{id}")
    public Community getCommunity(@PathVariable Long id) {
        return service.getCommunityById(id);
    }

    @GetMapping("/search")
    public List<Community> getByTags(
            @RequestParam List<String> tags,
            @RequestParam Long userId) {
        return service.getCommunitiesByTags(tags, userId);
    }

    @PutMapping("/{id}")
    public Community updateCommunity(
            @PathVariable Long id,
            @RequestBody UpdateCommunityRequest req,
            @RequestParam Long userId) {
        // TODO: Replace creator check with moderatorClient.isModerator(id, userId);
        //   throw FORBIDDEN if false.
        Community upd = new Community();
        upd.setDescription(req.getDescription());
        upd.setTags(req.getTags());
        return service.updateCommunity(id, upd, userId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommunity(
            @PathVariable Long id,
            @RequestParam Long userId) {
        // TODO: Replace creator check with moderatorClient.isModerator(id, userId);
        //         so moderators (not just creator) can delete communities.
        service.deleteCommunity(id, userId);
    }

    @PostMapping("/{id}/subscribe/{userId}")
    public Community subscribeUser(
            @PathVariable Long id,
            @PathVariable Long userId) {
        return service.subscribeUser(id, userId);
    }

    @DeleteMapping("/{id}/unsubscribe/{userId}")
    public Community unsubscribeUser(
            @PathVariable Long id,
            @PathVariable Long userId) {
        return service.unsubscribeUser(id, userId);
    }

    @PutMapping("/{id}/hide/{userId}")
    public Community hideCommunityForUser(
            @PathVariable Long id,
            @PathVariable Long userId) {
        return service.hideCommunityForUser(userId, id);
    }

    @PutMapping("/{id}/unhide/{userId}")
    public Community unhideCommunityForUser(
            @PathVariable Long id,
            @PathVariable Long userId) {
        return service.unhideCommunityForUser(userId, id);
    }
    // TODO: implement when ModeratorApp is ready
    // @PostMapping("/{id}/moderators/{userId}")
    // public ResponseEntity<Void> addModerator(
    //         @PathVariable Long id,
    //         @PathVariable Long userId,
    //         @RequestParam Long adminId) {
    //     // 1. Check adminId is a moderator via moderatorClient
    //     // 2. Call moderatorClient.addModerator(id, userId)
    //     // 3. Return 204 NO_CONTENT
    // }

    // @DeleteMapping("/{id}/moderators/{userId}")
    // public ResponseEntity<Void> removeModerator(
    //         @PathVariable Long id,
    //         @PathVariable Long userId,
    //         @RequestParam Long adminId) {
    //     // 1. Check adminId is a moderator via moderatorClient
    //     // 2. Call moderatorClient.removeModerator(id, userId)
    //     // 3. Return 204 NO_CONTENT
    // }
}

