package com.wroteit.UserApp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String biography;

    @ElementCollection
    private List<String> subscribedCommunities = new ArrayList<>();

    @ElementCollection
    private List<Long> followedUserIds = new ArrayList<>();

    @ElementCollection
    private List<Long> blockedUserIds = new ArrayList<>();

    // Getters, Setters, Constructors
}
