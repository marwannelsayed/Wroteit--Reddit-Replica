package com.wroteit.CommunitiesApp.dto;

import com.wroteit.CommunitiesApp.model.CommunityType;

public class CommunityRequest {
    private String name;
    private String description;
    private CommunityType type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CommunityType getType() {
        return type;
    }

    public void setType(CommunityType type) {
        this.type = type;
    }
}