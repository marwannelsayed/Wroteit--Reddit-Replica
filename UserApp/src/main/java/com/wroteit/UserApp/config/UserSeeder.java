package com.wroteit.UserApp.config;

import com.wroteit.UserApp.model.User;
import com.wroteit.UserApp.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev") // Only runs in dev environment
public class UserSeeder {

    private final UserService userService;

    public UserSeeder(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void seedUsers() {
        if (userService.userExists(1L)) {
            System.out.println(" Database already seeded. Skipping.");
            return;
        }

        try {
            userService.register(new User.Builder().username("eyadnasser").password("secret123").email("eyad@guc.edu.eg").biography("Software Engineer").build());
            userService.register(new User.Builder().username("johnDoe").password("pass123").email("john@example.com").biography("QA tester").build());
            userService.register(new User.Builder().username("aliceSmith").password("password").email("alice@example.com").biography("Moderator").build());
            userService.register(new User.Builder().username("michaelBrown").password("abc123").email("michael@example.com").biography("Product Manager").build());
            userService.register(new User.Builder().username("lindaWhite").password("test456").email("linda@example.com").biography("UX Designer").build());
            userService.register(new User.Builder().username("davidClark").password("secure789").email("david@example.com").biography("DevOps Engineer").build());
            userService.register(new User.Builder().username("susanLee").password("susanpass").email("susan@example.com").biography("Data Scientist").build());
            userService.register(new User.Builder().username("jamesHall").password("hallpass").email("james@example.com").biography("Full Stack Developer").build());
            userService.register(new User.Builder().username("emmaTurner").password("emmapass").email("emma@example.com").biography("AI Researcher").build());
            userService.register(new User.Builder().username("danielScott").password("daniel123").email("daniel@example.com").biography("Cloud Architect").build());
            userService.register(new User.Builder().username("oliviaKing").password("oliviapass").email("olivia@example.com").biography("Frontend Developer").build());
            userService.register(new User.Builder().username("liamWright").password("wrightpass").email("liam@example.com").biography("Backend Developer").build());
            userService.register(new User.Builder().username("sophiaGreen").password("sophiapass").email("sophia@example.com").biography("Tech Lead").build());
            userService.register(new User.Builder().username("noahYoung").password("noahpass").email("noah@example.com").biography("Intern").build());
            userService.register(new User.Builder().username("miaHill").password("miapass").email("mia@example.com").biography("Security Engineer").build());

            System.out.println("Dummy users inserted!");
        } catch (Exception e) {
            System.err.println("Seeder failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
