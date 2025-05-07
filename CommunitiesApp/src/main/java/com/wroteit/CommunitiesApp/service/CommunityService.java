package com.wroteit.CommunitiesApp.service;
// src/main/java/com/wroteit/communities/service/CommunityService.java

import com.wroteit.CommunitiesApp.model.Community;
import com.wroteit.CommunitiesApp.repository.CommunityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CommunityService {
    private final CommunityRepository repo;

    public CommunityService(CommunityRepository repo) {
        this.repo = repo;
    }

    public Community createCommunity(Community community) {
        if (repo.existsByName(community.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Community name already exists");
        }
        // default: creator becomes moderator → TODO replace with real ModeratorApp check
        // community.getSubscribers().add(creatorId);
        return repo.save(community);
    }

    public Community getCommunityById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Community not found"));
    }

    public List<Community> getCommunitiesByTags(List<String> tags, Long userId) {
        List<Community> matched = repo.findByTagsIn(tags);
        // filter out hidden for this user
        matched.removeIf(c -> c.getHiddenByUsers().contains(userId));
        return matched;
    }

    public Community updateCommunity(Long id, Community updated) {
        Community existing = getCommunityById(id);
        // only creator/moderator can update → placeholder
        // TODO: check userId against creator or ModeratorApp
        existing.setDescription(updated.getDescription());
        existing.setTags(updated.getTags());
        return repo.save(existing);
    }

    public void deleteCommunity(Long id) {
        Community existing = getCommunityById(id);
        // only creator/moderator can delete → placeholder
        // TODO: check userId against creator or ModeratorApp
        repo.deleteById(id);
    }

    public Community subscribeUser(Long communityId, Long userId) {
        Community c = getCommunityById(communityId);
        if (!c.getSubscribers().contains(userId)) {
            c.getSubscribers().add(userId);
            repo.save(c);
        }
        return c;
    }

    public Community unsubscribeUser(Long communityId, Long userId) {
        Community c = getCommunityById(communityId);
        c.getSubscribers().remove(userId);
        repo.save(c);
        return c;
    }

    public Community hideCommunityForUser(Long userId, Long communityId) {
        Community c = getCommunityById(communityId);
        if (!c.getHiddenByUsers().contains(userId)) {
            c.getHiddenByUsers().add(userId);
            repo.save(c);
        }
        return c;
    }

    public Community unhideCommunityForUser(Long userId, Long communityId) {
        Community c = getCommunityById(communityId);
        c.getHiddenByUsers().remove(userId);
        repo.save(c);
        return c;
    }
}
