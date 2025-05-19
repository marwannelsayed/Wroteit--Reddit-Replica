package com.wroteit.ThreadApp.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "threads")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThreadPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 10000)
    private String content;

    private String authorUsername;

    private String communityName;

    private LocalDateTime createdAt;

    private int upvotes;

    private int downvotes;

    @OneToMany(mappedBy = "thread", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
}
