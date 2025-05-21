package com.wroteit.ModerationApp.controller;

import com.wroteit.ModerationApp.model.Report;
import com.wroteit.ModerationApp.service.ModeratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/moderators")
public class ModeratorController {

    private final ModeratorService moderatorService;
    RestTemplate restTemplate;
    String baseUrl;

    @Autowired
    public ModeratorController(ModeratorService moderatorService) {
        this.moderatorService = moderatorService;
        restTemplate = new RestTemplate();
        baseUrl = "http://api-gateway:8080";
    }

    @GetMapping("/community/{communityId}")
    public List<Long> getAllModeratorsOfCommunity(String communityId){
        return moderatorService.getAllModeratorsOfCommunity(communityId);
    }

    @PostMapping("/reports")
    public Report fileReport(@RequestBody Report report) {
        Report savedReport = moderatorService.fileReport(report);

        List<Long> moderatorIds = restTemplate.exchange(
                baseUrl + "/moderators/community/" + report.getCommunityId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Long>>() {}
        ).getBody();

        for (Long modId : moderatorIds) {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("recipientId", modId);
            requestBody.put("message", "User with id " + report.getReporterId() +
                    " reported " + report.getEntityType() + " with id " +
                    report.getReportedEntityId() + " in community " + report.getCommunityId());

            restTemplate.postForObject(baseUrl + "/notifications/report", requestBody, Void.class);
        }

        return savedReport;
    }

    @GetMapping("/reports/{communityId}")
    public List<Report> findByCommunityId(String communityId){
        return moderatorService.findByCommunityId(communityId);
    }


    @DeleteMapping("/reports/{id}")
    public String closeReport(@PathVariable Long id) {
        return moderatorService.closeReport(id);
    }

    @PostMapping("/assign")
    public String assignModerator(@RequestBody Long userId, @RequestBody String communityId) {
        return moderatorService.assignModerator(userId, communityId);
    }

    @PostMapping("/ban")
    public String banUser(@RequestBody Long userId, @RequestBody String communityId) {
        Map<String, String> body = new HashMap<>();
        body.put("communityId", communityId);

        restTemplate.put(baseUrl + "/communities/ban/" + userId, body);


        return "User banned";
    }


    @DeleteMapping("/user/{userId}")
    public void deleteUserRecords(@PathVariable Long userId){
        moderatorService.deleteUserRecords(userId);
    }

    @DeleteMapping("/thread/{postId}")
    public String deleteThread(@PathVariable Long postId) {
       return "Not implemented";
    }

    @GetMapping("/{userId}/isModerator/{communityId}")
    public boolean checkUserIsModerator(@PathVariable Long userId, @PathVariable String communityId){
        return moderatorService.getAllModeratorsOfCommunity(communityId).contains(userId);
    }
}
