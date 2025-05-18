package com.wroteit.ModerationApp.controller;

import com.wroteit.ModerationApp.model.EntityType;
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

    @PostMapping("/reports")
    public Report fileReport(@RequestBody Report report) {
        return moderatorService.fileReport(report);
    }

    @GetMapping("/reports/{communityId}")
    public List<Report> findByCommunityId(Long communityId){
        return moderatorService.findByCommunityId(communityId);
    }


    @DeleteMapping("/reports/{id}")
    public String closeReport(@PathVariable Long id) {
        return moderatorService.closeReport(id);
    }

    @PostMapping("/assign")
    public String assignModerator(@RequestBody Long userId, @RequestBody Long communityId) {
        return moderatorService.assignModerator(userId, communityId);
    }

    @PostMapping("/ban")
    public String banUser(@RequestBody Long userId, @RequestBody Long communityId) {
        // TODO: Update community to have user banned
        return "User banned";
    }

    @DeleteMapping("/content/{entityType}/{postId}")
    public String deleteContent(@PathVariable EntityType entityType, @PathVariable Long postId) {
       // TODO: Update relevant table by deleting entity
        switch(entityType){
            case THREAD: // API call to delete thread
                break;
            case COMMENT: // API call to delete comment
                break;
            default: return "Invalid content type";
        }
        return entityType + "deleted successfully";
    }
}
