package com.wroteit.ModerationApp.controller;

import com.wroteit.ModerationApp.model.Report;
import com.wroteit.ModerationApp.service.ModeratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/moderators")
public class ModeratorController {

    private final ModeratorService moderatorService;

    @Autowired
    public ModeratorController(ModeratorService moderatorService) {
        this.moderatorService = moderatorService;
    }

    @GetMapping("/community/{communityId}")
    public List<Long> getAllModeratorsOfCommunity(String communityId){
        return moderatorService.getAllModeratorsOfCommunity(communityId);
    }

    @PostMapping("/reports")
    public Report fileReport(@RequestBody Report report) {
        return moderatorService.fileReport(report);
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
        // TODO: Update community to have user banned
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
