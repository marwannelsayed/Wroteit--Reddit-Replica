package com.wroteit.CommunitiesApp.controller;

import com.wroteit.CommunitiesApp.dto.CommunityRequest;
import com.wroteit.CommunitiesApp.model.Community;
import com.wroteit.CommunitiesApp.model.CommunityType;
import com.wroteit.CommunitiesApp.service.CommunityService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/communities")
public class CommunityController {
    private final CommunityService communityService;
    RestTemplate restTemplate;
    String baseUrl;

    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
        restTemplate = new RestTemplate();
        baseUrl = "http://gatewayapp:8080";
    }

    @PostMapping("/creatorID/{creatorID}")
    public Community createCommunity(@PathVariable(name = "creatorID") Long creatorID, @RequestBody CommunityRequest communityData) {
        Community community = communityService.createCommunity(creatorID, communityData.getName(), communityData.getDescription(), communityData.getType());

        Map<String, Object> body = new HashMap<>();
        body.put("userId", creatorID);
        body.put("communityId", community.getId());
        restTemplate.postForObject(baseUrl + "/moderators/assign", body, String.class);

        return community;
    }

    @GetMapping("/{id}")
    public Community getCommunityById(@PathVariable(name = "id") String id) {
        return communityService.getCommunityById(id);
    }

    @GetMapping("/tags/{userId}")
    public List<Community> getCommunitiesByTags(@RequestBody List<String> tags, @PathVariable(name = "userId") Long userId) {
        return communityService.getCommunitiesByTags(tags, userId);
    }

    @PostMapping("/subtag/{userId}")
    public List<Community> getCommunitiesBySubtag(@RequestBody String subtag, @PathVariable(name = "userId") Long userId) {
        return communityService.getCommunitiesByTagsCointain(subtag, userId);
    }

    @PutMapping("/{userId}")
    public Community updateCommunity(@RequestBody String communityId, @RequestBody Community newCommunity, @PathVariable(name = "userId") Long userId) {
        Boolean isModerator = restTemplate.getForObject(baseUrl + "/moderators/" + userId + "/isModerator/" + communityId, Boolean.class);
        if (isModerator == null || !isModerator) return null;
        return communityService.updateCommunity(communityId, newCommunity);
    }

    @DeleteMapping("/{userId}")
    public void deleteCommunity(@RequestBody String communityId, @PathVariable(name = "userId") Long userId) {
        Boolean isModerator = restTemplate.getForObject(baseUrl + "/moderators/" + userId + "/isModerator/" + communityId, Boolean.class);
        if (isModerator == null || !isModerator) return;
        communityService.deleteCommunity(communityId);
    }

    @PutMapping("/addTags/{userId}")
    public Community addTags(@RequestBody String communityId, @RequestBody List<String> tags, @PathVariable(name = "userId") Long userId) {
        Boolean isModerator = restTemplate.getForObject(baseUrl + "/moderators/" + userId + "/isModerator/" + communityId, Boolean.class);
        if (isModerator == null || !isModerator) return null;
        return communityService.addTags(communityId, tags);
    }

    @PutMapping("/removeTags/{userId}")
    public Community removeTags(@RequestBody String communityId, @RequestBody List<String> tags, @PathVariable(name = "userId") Long userId) {
        Boolean isModerator = restTemplate.getForObject(baseUrl + "/moderators/" + userId + "/isModerator/" + communityId, Boolean.class);
        if (isModerator == null || !isModerator) return null;
        return communityService.removeTags(communityId, tags);
    }

    @PutMapping("/subscribe/{userId}")
    public Community subscribeUser(@RequestBody String communityId, @PathVariable(name = "userId") Long userId) {
        return communityService.subscribeUser(communityId, userId);
    }

    @PutMapping("/unsubscribe/{userId}")
    public Community unsubscribeUser(@RequestBody String communityId, @PathVariable(name = "userId") Long userId) {
        return communityService.unsubscribeUser(communityId, userId);
    }

    @PutMapping("/hide/{userId}")
    public Community hideCommunityForUser(@RequestBody String communityId, @PathVariable(name = "userId") Long userId) {
        return communityService.hideCommunityForUser(userId, communityId);
    }

    @PutMapping("/unhide/{userId}")
    public Community unhideCommunityForUser(@RequestBody String communityId, @PathVariable(name = "userId") Long userId) {
        return communityService.unhideCommunityForUser(userId, communityId);
    }

    @PutMapping("/ban/{userId}")
    public Community banCommunityForUser(@RequestBody String communityId, @PathVariable(name = "userId") Long userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("recipientId", userId);
        body.put("message", "You have been banned from community with id " + communityId);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        restTemplate.postForObject(baseUrl + "/notifications/ban", request, Void.class);

        return communityService.banCommunityForUser(userId, communityId);
    }

    @GetMapping("/{userId}/checkUserBanned/{communityId}")
    public boolean checkUserBanned(@PathVariable(name = "userId") Long userId, @PathVariable(name = "communityId") String communityId){
        return communityService.checkUserBanned(userId, communityId);
    }

}

