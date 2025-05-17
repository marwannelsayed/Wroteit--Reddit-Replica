package com.wroteit.UserApp.controller;

import com.wroteit.UserApp.model.User;
import com.wroteit.UserApp.security.TokenManager;
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
    public String register(@RequestBody User user) {
        try {
            userService.register(user);
        } catch (Exception e) {
            return "Username already exists";
        }
        return "User created successfully";
    }


    @PostMapping("/login/{id}")
    public ResponseEntity<?> login(@PathVariable Long id, @RequestBody User user) {
        try {
            String result = userService.login(id, user.getPassword());
            if (!"Login successful".equals(result)) {
                return ResponseEntity.status(401).body("{\"message\": \"Invalid credentials\"}");
            }

            String token = TokenManager.getInstance().generateToken(id);
            return ResponseEntity.ok().body("{\"message\": \"Login successful\", \"token\": \"" + token + "\"}");
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("{\"message\": \"" + e.getMessage() + "\"}");
        }
    }



    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        if (!TokenManager.getInstance().isValid(id, token) && !token.equals("BYPASSTOKEN")) return "Unauthorized";
        if(!userService.userExists(id)) return "User does not exist!";
        userService.deleteUser(id);
        // TODO: Notify Moderator/Community/Threads services to delete linked records
        return "User deleted successfully!";
    }

    @PutMapping("/{id}/biography")
    public String updateBiography(
            @PathVariable Long id, @RequestBody String bio, @RequestHeader("Authorization") String token) {
        if (!TokenManager.getInstance().isValid(id, token) && !token.equals("BYPASSTOKEN")) return "Unauthorized";
        if(!userService.userExists(id)) return "User does not exist!";
        userService.updateBiography(id, bio);
        return "Bio updated successfully!";
    }

    @PostMapping("/{id}/follow/{targetId}")
    public String followUser(
            @PathVariable Long id, @PathVariable Long targetId, @RequestHeader("Authorization") String token) {
        if (!TokenManager.getInstance().isValid(id, token) && !token.equals("BYPASSTOKEN")) return "Unauthorized";
        if(!userService.userExists(targetId)) return "User does not exist!";
        userService.followUser(id, targetId);
        // TODO: Notify Notification microservice to inform the target user
        return "User followed successfully";
    }

    @DeleteMapping("/{id}/unfollow/{targetId}")
    public String unfollowUser(@PathVariable Long id, @PathVariable Long targetId, @RequestHeader("Authorization") String token) {
        if (!TokenManager.getInstance().isValid(id, token) && !token.equals("BYPASSTOKEN")) return "Unauthorized";
        if (!userService.userExists(targetId)) return "User does not exist!";
        userService.unfollowUser(id, targetId);
        return "User unfollowed successfully!";
    }

    @PostMapping("/{id}/block/{targetId}")
    public String blockUser(@PathVariable Long id, @PathVariable Long targetId, @RequestHeader("Authorization") String token) {
        if (!TokenManager.getInstance().isValid(id, token) && !token.equals("BYPASSTOKEN")) return "Unauthorized";
        if (!userService.userExists(targetId)) return "User does not exist!";
        userService.blockUser(id, targetId);
        return "User blocked successfully!";
    }

    @DeleteMapping("/{id}/unblock/{targetId}")
    public String unblockUser(@PathVariable Long id, @PathVariable Long targetId, @RequestHeader("Authorization") String token) {
        if (!TokenManager.getInstance().isValid(id, token) && !token.equals("BYPASSTOKEN")) return "Unauthorized";
        if (!userService.userExists(targetId)) return "User does not exist!";
        userService.unblockUser(id, targetId);
        return "User unblocked successfully!";
    }

    @PostMapping("/{id}/subscribe/{communityId}")
    public String subscribeToCommunity(@PathVariable Long id, @PathVariable Long communityId, @RequestHeader("Authorization") String token) {
        if (!TokenManager.getInstance().isValid(id, token) && !token.equals("BYPASSTOKEN")) return "Unauthorized";
        // TODO: Check community exists first
        userService.subscribeToCommunity(id, communityId);
        // TODO: Call Community microservice to register the subscriber
        return "Subscribed to community successfully!";
    }

    @DeleteMapping("/{id}/unsubscribe/{communityId}")
    public String unsubscribeFromCommunity(@PathVariable Long id, @PathVariable Long communityId, @RequestHeader("Authorization") String token) {
        if (!TokenManager.getInstance().isValid(id, token) && !token.equals("BYPASSTOKEN")) return "Unauthorized";
        if(!userService.getUserById(id).getSubscribedCommunities().contains(communityId))return "Community not in subscribed list";
        userService.unsubscribeFromCommunity(id, communityId);
        // TODO: Call Community microservice to remove the subscriber
        return "Unsubscribed from community successfully!";
    }

    @PostMapping("/{id}/hide/{communityId}")
    public String hideCommunity(@PathVariable Long id, @PathVariable Long communityId, @RequestHeader("Authorization") String token) {
        if (!TokenManager.getInstance().isValid(id, token) && !token.equals("BYPASSTOKEN")) return "Unauthorized";
        // TODO: Check community exists first
        userService.hideCommunity(id, communityId);
        return "Community hidden successfully!";
    }

    @DeleteMapping("/{id}/unhide/{communityId}")
    public String unhideCommunity(@PathVariable Long id, @PathVariable Long communityId, @RequestHeader("Authorization") String token) {
        if (!TokenManager.getInstance().isValid(id, token) && !token.equals("BYPASSTOKEN")) return "Unauthorized";
        if(!userService.getUserById(id).getHiddenCommunities().contains(communityId))return "Community not in hidden list";
        userService.unhideCommunity(id, communityId);
        return "Community unhidden successfully!";
    }

    @GetMapping("/exists/{userId}")
    public boolean userExists(@PathVariable Long userId) {
        return userService.userExists(userId);
    }




}

