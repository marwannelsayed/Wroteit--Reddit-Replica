
package main.java.com.wroteit.ModerationApp.command;

import com.wroteit.ModerationApp.repository.ModeratorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BanUserCommand implements ModerationCommand {
    private final ModeratorRepository moderatorRepository;
    private final Long userId;
    private final Long communityId;
    private final Long moderatorId;
    
    public BanUserCommand(ModeratorRepository moderatorRepository, Long userId, Long communityId, Long moderatorId) {
        this.moderatorRepository = moderatorRepository;
        this.userId = userId;
        this.communityId = communityId;
        this.moderatorId = moderatorId;
    }
    
    @Override
    public void execute() {
        // Verify the moderator exists for this community
        if (!moderatorRepository.existsByUserIdAndCommunityId(moderatorId, communityId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized to ban users in this community");
        }
        
        // TODO: Integration with UserApp - Add API call to ban the user from this community
        // Example: Make HTTP POST request to UserApp API with userId and communityId
        
        // TODO: Integration with NotificationApp - Send notification about the ban to the user
        // Example: Make HTTP POST request to NotificationApp API with ban details
    }
}