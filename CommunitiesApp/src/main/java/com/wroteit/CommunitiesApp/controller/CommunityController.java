package com.wroteit.CommunitiesApp.controller;

import com.wroteit.CommunitiesApp.model.Community;
import com.wroteit.CommunitiesApp.model.CommunityType;
import com.wroteit.CommunitiesApp.service.CommunityService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/communities")
public class CommunityController {
    private final CommunityService communityService;

    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @PostMapping("/creatorID")
    public Community createCommunity(@PathVariable Long creatorID, @RequestBody String name, @RequestBody String description, @RequestBody CommunityType type){
        Community community = communityService.createCommunity(creatorID, name, description, type);
        // TODO: Add api call to make record in moderator table for new community and moderator
        return community;
    }

    @GetMapping("/{id}")
    public Community getCommunityById(@PathVariable Long id) {
        return communityService.getCommunityById(id);
    }

    @GetMapping("/tags/{userId}")
    public List<Community> getCommunitiesByTags(@RequestBody List<String> tags, @PathVariable Long userId){
        return communityService.getCommunitiesByTags(tags, userId);
    }

    @GetMapping("/subtag/{userId}")
    public List<Community> getCommunitiesBySubtag(@RequestBody String subtag, @PathVariable Long userId){
        return communityService.getCommunitiesByTagsCointain(subtag, userId);
    }

    @PutMapping("/{userId}")
    public Community updateCommunity(@RequestBody Long communityId, @RequestBody Community newCommunity, @PathVariable Long userId){
        // TODO: Validate user is a moderator of the community from moderator table via API call
        return communityService.updateCommunity(communityId, newCommunity);
    }

    @DeleteMapping("/{userId}")
    public void deleteCommunity(@RequestBody Long communityId, @PathVariable Long userId){
        // TODO: Validate user is a moderator of the community from moderator table via API call
        communityService.deleteCommunity(communityId);
        // TODO: Delete threads in deleted community
    }

    @PutMapping("/addTags/{userId}")
    public Community addTags(@RequestBody Long communityId, @RequestBody List<String> tags, @PathVariable Long userId){
        // TODO: Validate user is a moderator of the community from moderator table via API call
        return communityService.addTags(communityId, tags);
    }

    @PutMapping("/removeTags/{userId}")
    public Community removeTags(@RequestBody Long communityId, @RequestBody List<String> tags, @PathVariable Long userId){
        // TODO: Validate user is a moderator of the community from moderator table via API call
        return communityService.removeTags(communityId, tags);
    }

    @PutMapping("/subscribe/{userId}")
    public Community subscribeUser(@RequestBody Long communityId, @PathVariable Long userId){
        return communityService.subscribeUser(communityId, userId);
    }

    @PutMapping("/unsubscribe/{userId}")
    public Community unsubscribeUser(@RequestBody Long communityId, @PathVariable Long userId){
        return communityService.unsubscribeUser(communityId, userId);
    }

    @PutMapping("/hide/{userId}")
    public Community hideCommunityForUser(@RequestBody Long communityId, @PathVariable Long userId){
        return communityService.hideCommunityForUser(communityId, userId);
    }

    @PutMapping("/unhide/{userId}")
    public Community unhideCommunityForUser(@RequestBody Long communityId, @PathVariable Long userId){
        return communityService.unhideCommunityForUser(communityId, userId);
    }


}

