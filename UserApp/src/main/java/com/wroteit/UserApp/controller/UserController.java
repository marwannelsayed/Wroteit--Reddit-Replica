package com.wroteit.UserApp.controller;

import com.wroteit.UserApp.DTO.*;
import com.wroteit.UserApp.model.User;
import com.wroteit.UserApp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest req) {
        User user = new User.Builder()
                .username(req.getUsername())
                .password(req.getPassword())
                .email(req.getEmail())
                .build();

        User savedUser = userService.register(user);
        return ResponseEntity.ok(mapToUserResponse(savedUser));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest req) {
        String result = userService.login(req.getUsername(), req.getPassword());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(mapToUserResponse(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        // TODO: Notify Moderator/Community/Threads services to delete linked records
        return ResponseEntity.ok("User with ID " + id + " deleted successfully");
    }

    @PutMapping("/{id}/biography")
    public ResponseEntity<UserResponse> updateBiography(
            @PathVariable Long id,
            @RequestBody String bio) {
        User user = userService.updateBiography(id, bio);
        return ResponseEntity.ok(mapToUserResponse(user));
    }

    @PostMapping("/{id}/follow/{targetId}")
    public ResponseEntity<UserResponse> followUser(
            @PathVariable Long id,
            @PathVariable Long targetId) {
        User user = userService.followUser(id, targetId);
        // TODO: Notify Notification microservice to inform the target user
        return ResponseEntity.ok(mapToUserResponse(user));
    }

    @DeleteMapping("/{id}/unfollow/{targetId}")
    public ResponseEntity<UserResponse> unfollowUser(
            @PathVariable Long id,
            @PathVariable Long targetId) {
        User user = userService.unfollowUser(id, targetId);
        return ResponseEntity.ok(mapToUserResponse(user));
    }

    @PostMapping("/{id}/block/{targetId}")
    public ResponseEntity<UserResponse> blockUser(
            @PathVariable Long id,
            @PathVariable Long targetId) {
        User user = userService.blockUser(id, targetId);
        // TODO: Optionally notify Community to sever shared connections
        return ResponseEntity.ok(mapToUserResponse(user));
    }

    @DeleteMapping("/{id}/unblock/{targetId}")
    public ResponseEntity<UserResponse> unblockUser(
            @PathVariable Long id,
            @PathVariable Long targetId) {
        User user = userService.unblockUser(id, targetId);
        return ResponseEntity.ok(mapToUserResponse(user));
    }

    @PostMapping("/{id}/subscribe/{communityId}")
    public ResponseEntity<UserResponse> subscribeToCommunity(
            @PathVariable Long id,
            @PathVariable String communityId) {
        User user = userService.subscribeToCommunity(id, communityId);
        // TODO: Call Community microservice to register the subscriber
        return ResponseEntity.ok(mapToUserResponse(user));
    }

    @DeleteMapping("/{id}/unsubscribe/{communityId}")
    public ResponseEntity<UserResponse> unsubscribeFromCommunity(
            @PathVariable Long id,
            @PathVariable String communityId) {
        User user = userService.unsubscribeFromCommunity(id, communityId);
        // TODO: Call Community microservice to remove the subscriber
        return ResponseEntity.ok(mapToUserResponse(user));
    }

    @PostMapping("/{id}/hide/{communityId}")
    public ResponseEntity<UserResponse> hideCommunity(
            @PathVariable Long id,
            @PathVariable String communityId) {
        User user = userService.hideCommunity(id, communityId);
        return ResponseEntity.ok(mapToUserResponse(user));
    }

    @DeleteMapping("/{id}/unhide/{communityId}")
    public ResponseEntity<UserResponse> unhideCommunity(
            @PathVariable Long id,
            @PathVariable String communityId) {
        User user = userService.unhideCommunity(id, communityId);
        return ResponseEntity.ok(mapToUserResponse(user));
    }


    // Check if user exists (integration support)
    @GetMapping("/exists/{userId}")
    public ResponseEntity<Boolean> userExists(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.userExists(userId));
    }

    //  Helper method to convert User → DTO
    private UserResponse mapToUserResponse(User user) {
        return new UserResponse.Builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .biography(user.getBiography())
                .following(user.getFollowing())
                .blockedUsers(user.getBlockedUsers())
                .subscribedCommunities(user.getSubscribedCommunities())
                .hiddenCommunities(user.getHiddenCommunities())
                .build();
    }

}

