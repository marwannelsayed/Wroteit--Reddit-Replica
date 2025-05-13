package com.wroteit.ModerationApp.service;

import com.wroteit.ModerationApp.command.AssignModeratorCommand;
import com.wroteit.ModerationApp.command.ModerationCommand;
import com.wroteit.ModerationApp.model.Moderator;
import com.wroteit.ModerationApp.repository.ModeratorRepository;
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

    // karim osama
}