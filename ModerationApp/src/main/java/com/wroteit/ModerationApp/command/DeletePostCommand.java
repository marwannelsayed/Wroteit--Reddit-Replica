
package com.wroteit.ModerationApp.command;

import com.wroteit.ModerationApp.repository.ModeratorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DeletePostCommand implements ModerationCommand {
    private final ModeratorRepository moderatorRepository;
    private final Long postId;
    private final Long communityId;
    private final Long moderatorId;
    
    public DeletePostCommand(ModeratorRepository moderatorRepository, Long postId, Long communityId, Long moderatorId) {
        this.moderatorRepository = moderatorRepository;
        this.postId = postId;
        this.communityId = communityId;
        this.moderatorId = moderatorId;
    }
    
    @Override
    public void execute() {
        // Verify the moderator exists for this community
        if (!moderatorRepository.existsByUserIdAndCommunityId(moderatorId, communityId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized to delete posts in this community");
        }
        
        // TODO: Integration with ThreadsApp - Add API call to delete the post
        // Example: Make HTTP DELETE request to ThreadsApp API for the specific postId
        
        
        // TODO: Integration with NotificationApp - Send notification about deleted post
        // Example: Make HTTP POST request to NotificationApp API with deletion details
    }
}