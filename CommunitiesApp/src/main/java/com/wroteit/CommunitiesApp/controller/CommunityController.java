package com.wroteit.CommunitiesApp.controller;
// src/main/java/com/wroteit/communities/controller/CommunityController.java

import com.wroteit.CommunitiesApp.model.Community;
import com.wroteit.CommunitiesApp.model.CreateCommunityRequest;
import com.wroteit.CommunitiesApp.model.UpdateCommunityRequest;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Community createCommunity(@RequestBody CreateCommunityRequest req) {
        Community c = new Community();
        c.setName(req.getName());
        c.setDescription(req.getDescription());
        if (req.getTags() != null) c.setTags(req.getTags());
        return service.createCommunity(c);
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
            @RequestBody UpdateCommunityRequest req) {
        // only creator (future: moderator) can update
        Community upd = new Community();
        upd.setDescription(req.getDescription());
        upd.setTags(req.getTags());
        return service.updateCommunity(id, upd);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommunity(@PathVariable Long id) {
        // only creator (future: moderator) can delete
        service.deleteCommunity(id);
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
}

