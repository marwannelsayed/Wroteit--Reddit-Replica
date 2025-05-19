package com.wroteit.ModerationApp.command;

import com.wroteit.ModerationApp.model.Moderator;
import com.wroteit.ModerationApp.repository.ModeratorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AssignModeratorCommand implements ModerationCommand {
    private final ModeratorRepository moderatorRepository;
    private final Long userId;
    private final String communityId;

    public AssignModeratorCommand(ModeratorRepository moderatorRepository, Long userId, String communityId) {
        this.moderatorRepository = moderatorRepository;
        this.userId = userId;
        this.communityId = communityId;
    }

    @Override
    public void execute() {

        if (moderatorRepository.existsByUserIdAndCommunityId(userId, communityId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User is already a moderator for this community");
        }


        Moderator moderator = new Moderator(userId, communityId);
        moderatorRepository.save(moderator);

    }
}