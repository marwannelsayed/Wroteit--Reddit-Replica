package com.wroteit.CommunitiesApp.service;
// src/main/java/com/wroteit/CommunitiesApp/service/CommunityService.java

import com.wroteit.CommunitiesApp.model.Community;
import com.wroteit.CommunitiesApp.model.CommunityFactory;
import com.wroteit.CommunitiesApp.model.CommunityTagBuilder;
import com.wroteit.CommunitiesApp.model.CommunityType;
import com.wroteit.CommunitiesApp.repository.CommunityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final CommunityFactory communityFactory;

    public CommunityService(CommunityRepository communityRepository, CommunityFactory communityFactory) {
        this.communityRepository = communityRepository;
        this.communityFactory = communityFactory;
    }

    public Community createCommunity(String name, String description, CommunityType type, Long userId) {
        if (communityRepository.existsByName(name)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Community name already exists");
        }
        Community newCommunity = communityFactory.create(name, description, type);
        newCommunity.getSubscribers().add(userId);
        return communityRepository.save(newCommunity);
    }

    public Community getCommunityById(Long id) {
        return communityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Community not found"));
    }

    public List<Community> getCommunitiesByTags(List<String> tags, Long userId) {
        List<Community> matched = communityRepository.findByTagsIn(tags);
        matched.removeIf(c -> c.getHiddenByUsers().contains(userId));
        return matched;
    }


    public List<Community> getCommunitiesByTagsCointain(String subtag, Long userId) {
        List<Community> matched = communityRepository.findByTagsContaining(subtag);
        matched.removeIf(c -> c.getHiddenByUsers().contains(userId));
        return matched;
    }


    public Community updateCommunity(Long id, Community updated) {
        Community existing = getCommunityById(id);
        existing.setDescription(updated.getDescription());
        existing.setTags(updated.getTags());
        existing.setSubscribers(updated.getSubscribers());
        existing.setThreads(updated.getThreads());
        existing.setHiddenByUsers(updated.getHiddenByUsers());
        return communityRepository.save(existing);
    }


    public void deleteCommunity(Long id, Long userId) {
        Community existing = getCommunityById(id);
        if (existing == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Community not found");
        }
        communityRepository.deleteById(id);
    }

    public Community addTags(Long communityId, List<String> tags) {
        Community c = getCommunityById(communityId);
        if (c == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Community not found");
        }
        CommunityTagBuilder communityTagBuilder = new CommunityTagBuilder(c);
        for(String tag: tags){
            communityTagBuilder.addTag(tag);
        }
        c = communityTagBuilder.build();

        return communityRepository.save(c);
    }

    public Community removeTags(Long communityId, List<String> tags) {
        Community c = getCommunityById(communityId);
        if (c == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Community not found");
        }
        CommunityTagBuilder communityTagBuilder = new CommunityTagBuilder(c);
        for(String tag: tags){
            communityTagBuilder.removeTag(tag);
        }
        c = communityTagBuilder.build();

        return communityRepository.save(c);
    }

    public Community subscribeUser(Long communityId, Long userId) {
        Community c = getCommunityById(communityId);
        if (c == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Community not found");
        }
        if (!c.getSubscribers().contains(userId)) {
            c.getSubscribers().add(userId);
            communityRepository.save(c);
        }
        return c;
    }

    public Community unsubscribeUser(Long communityId, Long userId) {
        Community c = getCommunityById(communityId);
        if (c == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Community not found");
        }
        c.getSubscribers().remove(userId);
        communityRepository.save(c);
        return c;
    }

    public Community hideCommunityForUser(Long userId, Long communityId) {
        Community c = getCommunityById(communityId);
        if (!c.getHiddenByUsers().contains(userId)) {
            c.getHiddenByUsers().add(userId);
            communityRepository.save(c);
        }
        return c;
    }

    public Community unhideCommunityForUser(Long userId, Long communityId) {
        Community c = getCommunityById(communityId);
        c.getHiddenByUsers().remove(userId);
        communityRepository.save(c);
        return c;
    }
}