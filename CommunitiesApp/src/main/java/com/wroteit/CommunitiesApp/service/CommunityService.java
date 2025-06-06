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

    public Community createCommunity(Long userId, String name, String description, CommunityType type) {
        if (communityRepository.existsByName(name)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Community name already exists");
        }
        Community newCommunity = communityFactory.create(name, description, type);
        newCommunity.getSubscribers().add(userId);
        return communityRepository.save(newCommunity);
    }

    public Community getCommunityById(String id) {
        return communityRepository.findById(id)
                .orElse(null);
    }


    public List<Community> getCommunitiesByTags(List<String> tags, Long userId) {
        List<Community> matched = communityRepository.findByTagsIn(tags);
        matched.removeIf(c -> c.getHiddenByUsers().contains(userId));
        return matched;
    }


    public List<Community> getCommunitiesByTagsCointain(String subtag, Long userId) {
        List<Community> matched = communityRepository.findByTagSubstring(subtag);
        matched.removeIf(c -> c.getHiddenByUsers().contains(userId));
        return matched;
    }


    public Community updateCommunity(String id, String newDescription) {
        Community existing = getCommunityById(id);
        existing.setDescription(newDescription);
        return communityRepository.save(existing);
    }


    public void deleteCommunity(String id) {
        Community existing = getCommunityById(id);
        if (existing == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Community not found");
        }
        communityRepository.deleteById(id);
    }

    public Community addTags(String communityId, List<String> tags) {
        Community c = communityRepository.findById(communityId)
                .orElse(null);
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

    public Community removeTags(String communityId, List<String> tags) {
        Community c = communityRepository.findById(communityId)
                .orElse(null);
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

    public Community subscribeUser(String communityId, Long userId) {
        Community c = communityRepository.findById(communityId)
                .orElse(null);
        if (c == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Community not found");
        }
        if (!c.getSubscribers().contains(userId)) {
            c.getSubscribers().add(userId);
            communityRepository.save(c);
        }
        return c;
    }

    public Community unsubscribeUser(String communityId, Long userId) {
        Community c = communityRepository.findById(communityId)
                .orElse(null);
        if (c == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Community not found");
        }
        c.getSubscribers().remove(userId);
        communityRepository.save(c);
        return c;
    }

    public Community hideCommunityForUser(Long userId, String communityId) {
        Community c = communityRepository.findById(communityId)
                .orElse(null);
        if (!c.getHiddenByUsers().contains(userId)) {
            c.getHiddenByUsers().add(userId);
            communityRepository.save(c);
        }
        return c;
    }

    public Community unhideCommunityForUser(Long userId, String communityId) {
        Community c = communityRepository.findById(communityId)
                .orElse(null);
        c.getHiddenByUsers().remove(userId);
        communityRepository.save(c);
        return c;
    }

    public Community banCommunityForUser(Long userId, String communityId) {
        Community c = communityRepository.findById(communityId)
                .orElse(null);
        if(c==null)return null;
        if (!c.getBannedUsers().contains(userId)) {
            c.getBannedUsers().add(userId);
            communityRepository.save(c);
        }
        return c;
    }


    public boolean checkUserBanned(Long userId, String communityId) {
        Community community = communityRepository.findById(communityId)
                .orElse(null);
        return community.getBannedUsers().contains(userId);
    }
}