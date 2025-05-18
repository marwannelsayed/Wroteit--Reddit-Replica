package com.example.threadapp.config;

import com.example.threadapp.model.Comment;
import com.example.threadapp.model.Thread;
import com.example.threadapp.model.Vote;
import com.example.threadapp.repository.CommentRepository;
import com.example.threadapp.repository.ThreadRepository;
import com.example.threadapp.repository.VoteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


@Component
public class DataSeeder implements CommandLineRunner {

    private final ThreadRepository threadRepository;
    private final CommentRepository commentRepository;
    private final VoteRepository voteRepository;

    public DataSeeder(ThreadRepository threadRepository, CommentRepository commentRepository, VoteRepository voteRepository) {
        this.threadRepository = threadRepository;
        this.commentRepository = commentRepository;
        this.voteRepository = voteRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Clean up existing data (optional, for development)
        // threadRepository.deleteAll();
        // commentRepository.deleteAll();
        // voteRepository.deleteAll();

        // Create Sample Users (IDs)
        String user1Id = "user_alpha_123";
        String user2Id = "user_beta_456";
        String user3Id = "user_gamma_789";

        // Create Sample Threads
        Thread thread1 = new Thread("First Sample Thread", "This is the content of the first sample thread. Discuss!", user1Id);
        thread1.setCreatedAt(LocalDateTime.now().minusDays(2));
        thread1.setUpdatedAt(LocalDateTime.now().minusDays(2));

        Thread thread2 = new Thread("Exploring Spring Boot 3", "Let's talk about new features in Spring Boot 3 and JDK 23.", user2Id);
        thread2.setCreatedAt(LocalDateTime.now().minusDays(1));
        thread2.setUpdatedAt(LocalDateTime.now().minusDays(1));

        Thread thread3 = new Thread("MongoDB Best Practices", "Share your tips for designing schemas and optimizing queries in MongoDB.", user1Id);

        threadRepository.saveAll(Arrays.asList(thread1, thread2, thread3));

        // Create Sample Comments
        // Comments for Thread 1
        Comment comment1_1 = new Comment(thread1.getId(), user2Id, "Great topic! I have a few thoughts on this.");
        comment1_1.setCreatedAt(LocalDateTime.now().minusDays(2).plusHours(1));
        comment1_1.setUpdatedAt(LocalDateTime.now().minusDays(2).plusHours(1));

        Comment comment1_2 = new Comment(thread1.getId(), user3Id, "Interesting point, @user_beta_456. I agree.");
        comment1_2.setParentId(comment1_1.getId()); // Nested comment
        comment1_2.setCreatedAt(LocalDateTime.now().minusDays(2).plusHours(2));
        comment1_2.setUpdatedAt(LocalDateTime.now().minusDays(2).plusHours(2));

        // Comments for Thread 2
        Comment comment2_1 = new Comment(thread2.getId(), user1Id, "I'm particularly interested in virtual threads with JDK 23.");

        commentRepository.saveAll(Arrays.asList(comment1_1, comment1_2, comment2_1));

        // Create Sample Votes
        // Votes for Thread 1
        Vote voteT1_U1 = new Vote(user2Id, thread1.getId(), Vote.TargetType.THREAD, Vote.VoteType.UPVOTE);
        Vote voteT1_U2 = new Vote(user3Id, thread1.getId(), Vote.TargetType.THREAD, Vote.VoteType.UPVOTE);
        thread1.setUpvotes(2);
        threadRepository.save(thread1);

        // Votes for Comment 1_1
        Vote voteC11_U1 = new Vote(user1Id, comment1_1.getId(), Vote.TargetType.COMMENT, Vote.VoteType.UPVOTE);
        comment1_1.setUpvotes(1);
        commentRepository.save(comment1_1);
        
        // Votes for Thread 2
        Vote voteT2_U1 = new Vote(user3Id, thread2.getId(), Vote.TargetType.THREAD, Vote.VoteType.DOWNVOTE);
        thread2.setDownvotes(1);
        threadRepository.save(thread2);

        voteRepository.saveAll(Arrays.asList(voteT1_U1, voteT1_U2, voteC11_U1, voteT2_U1));

        System.out.println("Sample data seeded successfully!");
    }
}

