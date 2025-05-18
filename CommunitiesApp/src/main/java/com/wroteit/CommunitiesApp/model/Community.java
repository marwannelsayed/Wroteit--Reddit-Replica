package com.wroteit.CommunitiesApp.model;


import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "communities")
public class Community {
    @Id
    private String id;

    @Indexed(unique = true)
    private String name;
    private String description;
    private List<Long> subscribers = new ArrayList<>();
    private List<String> tags = new ArrayList<>();
    private List<Long> threads = new ArrayList<>();
    private List<Long> hiddenByUsers = new ArrayList<>();
    private List<Long> bannedUsers = new ArrayList<>();

    public Community() {}

    public Community(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getId() { return id; }

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

    public List<Long> getBannedUsers() {
        return bannedUsers;
    }

    public void setBannedUsers(List<Long> bannedUsers) {
        this.bannedUsers = bannedUsers;
    }
}
