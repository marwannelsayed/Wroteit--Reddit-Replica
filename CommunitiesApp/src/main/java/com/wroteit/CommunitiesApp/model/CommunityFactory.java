package com.wroteit.CommunitiesApp.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommunityFactory {
    public Community create(String name, String description, CommunityType type) {
        Community community = new Community(name, description);
        switch (type) {
            case FOOD -> community.setTags(new ArrayList<>(List.of("food", "nutrition")));
            case SPORTS -> community.setTags(new ArrayList<>(List.of("sports", "fitness")));
            case GAMING -> community.setTags(new ArrayList<>(List.of("gaming", "fun")));
            case POLITICS -> community.setTags(new ArrayList<>(List.of("politics", "important")));
        }
        return community;
    }
}


