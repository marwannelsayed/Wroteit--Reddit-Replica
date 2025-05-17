package com.wroteit.CommunitiesApp.model;

public class CommunityTagBuilder {
    private final Community community;

    public CommunityTagBuilder(Community community) {
        this.community = community;
    }

    public CommunityTagBuilder addTag(String tag) {
        if (!community.getTags().contains(tag)) {
            community.getTags().add(tag);
        }
        return this;
    }

    public CommunityTagBuilder removeTag(String tag) {
        community.getTags().remove(tag);
        return this;
    }

    public Community build() {
        return community;
    }
}
