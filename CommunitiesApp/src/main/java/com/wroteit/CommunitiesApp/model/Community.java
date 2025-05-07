package com.wroteit.CommunitiesApp.model;
// src/main/java/com/wroteit/communities/model/Community.java

import java.util.ArrayList;
import java.util.List;

public class Community {
    private Long id;
    private String name;
    private String description;
    private List<Long> subscribers = new ArrayList<>();
    private List<String> tags = new ArrayList<>();
    private List<Long> threads = new ArrayList<>();
    private List<Long> hiddenByUsers = new ArrayList<>();

    // --- Constructors ---
    public Community() {}
    public Community(Long id, String name, String description, List<String> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tags = tags != null ? tags : new ArrayList<>();
    }

    // --- Getters & Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<Long> getSubscribers() { return subscribers; }
    public void setSubscribers(List<Long> subscribers) { this.subscribers = subscribers; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public List<Long> getThreads() { return threads; }
    public void setThreads(List<Long> threads) { this.threads = threads; }

    public List<Long> getHiddenByUsers() { return hiddenByUsers; }
    public void setHiddenByUsers(List<Long> hiddenByUsers) { this.hiddenByUsers = hiddenByUsers; }
}
