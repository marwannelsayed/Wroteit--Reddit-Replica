package com.wroteit.ModerationApp.service;

import com.wroteit.ModerationApp.command.AssignModeratorCommand;
import com.wroteit.ModerationApp.command.ModerationCommand;
import com.wroteit.ModerationApp.model.Moderator;
import com.wroteit.ModerationApp.repository.ModeratorRepository;

import com.wroteit.ModerationApp.command.BanUserCommand;
import com.wroteit.ModerationApp.command.DeletePostCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModeratorService {
    private final ModeratorRepository moderatorRepository;

    @Autowired
    public ModeratorService(ModeratorRepository moderatorRepository) {
        this.moderatorRepository = moderatorRepository;
    }

    public void assignModerator(Long userId, Long communityId) {
        ModerationCommand command = new AssignModeratorCommand(moderatorRepository, userId, communityId);
        command.execute();
    }

    public List<Moderator> getModeratorsForCommunity(Long communityId) {
        return moderatorRepository.findByCommunityId(communityId);
    }

    public List<Moderator> getCommunitiesModeratedByUser(Long userId) {
        return moderatorRepository.findByUserId(userId);
    }

   
    public void banUser(Long userId, Long communityId, Long moderatorId) {
        BanUserCommand command = new BanUserCommand(moderatorRepository, userId, communityId, moderatorId);
        command.execute();
    }
     public void deletePost(Long postId, Long communityId, Long moderatorId) {
        DeletePostCommand command = new DeletePostCommand(moderatorRepository, postId, communityId, moderatorId);
        command.execute();
    }
    public boolean isModeratorForCommunity(Long userId, Long communityId) {
        return moderatorRepository.existsByUserIdAndCommunityId(userId, communityId);
    }
 // TODO: Integration with UserApp - Add methods to retrieve user information
    // Example: getUserDetails(Long userId) to fetch user details from UserApp
//    public void getUserDetails(Long userId) {
//        User user = userAppClient.getUserDetails(userId);
//        return user;
//
//
//    }
    
    // TODO: Integration with ThreadsApp - Add methods to retrieve thread information
    // Example: getThreadDetails(Long threadId) to fetch thread details from ThreadsApp
//    public Thread getThreadDetails(Long threadId) {
//        Thread thread = threadsAppClient.getThreadDetails(threadId);
//        return thread;
//    }
}

