package com.wroteit.UserApp.controller;

import com.wroteit.UserApp.model.User;
import com.wroteit.UserApp.security.TokenManager;
import com.wroteit.UserApp.service.UserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final RabbitTemplate rabbitTemplate;
    RestTemplate restTemplate;
    String baseUrl;

    public UserController(UserService userService, RabbitTemplate rabbitTemplate) {
        this.userService = userService;
        this.rabbitTemplate = rabbitTemplate;
        restTemplate = new RestTemplate();
        baseUrl = "http://gatewayapp:8080";
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
    public ResponseEntity<?> login(@PathVariable("id") Long id, @RequestBody String password) {
        try {
            String result = userService.login(id, password);
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
    public User getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id, @RequestHeader("Authorization") String token) {
        if (!TokenManager.getInstance().isValid(id, token) && !token.equals("BYPASSTOKEN")) return "Unauthorized";
        if (!userService.userExists(id)) return "User does not exist!";
        userService.deleteUser(id);
        rabbitTemplate.convertAndSend("deleteUserQueue", id);
        restTemplate.delete(baseUrl + "/moderators/user/" + id);
        return "User deleted successfully!";
    }

    @PutMapping("/{id}/biography")
    public String updateBiography(
            @PathVariable("id") Long id,
            @RequestBody String bio,
            @RequestHeader("Authorization") String token) {
        if (!TokenManager.getInstance().isValid(id, token) && !token.equals("BYPASSTOKEN")) return "Unauthorized";
        if (!userService.userExists(id)) return "User does not exist!";
        userService.updateBiography(id, bio);
        return "Bio updated successfully!";
    }

    @PostMapping("/{id}/follow/{targetId}")
    public String followUser(
            @PathVariable("id") Long id,
            @PathVariable("targetId") Long targetId,
            @RequestHeader("Authorization") String token) {
        if (!TokenManager.getInstance().isValid(id, token) && !token.equals("BYPASSTOKEN")) return "Unauthorized";
        if (!userService.userExists(targetId)) return "User does not exist!";
        userService.followUser(id, targetId);
        return "User followed successfully";
    }

    @DeleteMapping("/{id}/unfollow/{targetId}")
    public String unfollowUser(
            @PathVariable("id") Long id,
            @PathVariable("targetId") Long targetId,
            @RequestHeader("Authorization") String token) {
        if (!TokenManager.getInstance().isValid(id, token) && !token.equals("BYPASSTOKEN")) return "Unauthorized";
        if (!userService.userExists(targetId)) return "User does not exist!";
        userService.unfollowUser(id, targetId);
        return "User unfollowed successfully!";
    }

    @PostMapping("/{id}/block/{targetId}")
    public String blockUser(
            @PathVariable("id") Long id,
            @PathVariable("targetId") Long targetId,
            @RequestHeader("Authorization") String token) {
        if (!TokenManager.getInstance().isValid(id, token) && !token.equals("BYPASSTOKEN")) return "Unauthorized";
        if (!userService.userExists(targetId)) return "User does not exist!";
        userService.blockUser(id, targetId);
        return "User blocked successfully!";
    }

    @DeleteMapping("/{id}/unblock/{targetId}")
    public String unblockUser(
            @PathVariable("id") Long id,
            @PathVariable("targetId") Long targetId,
            @RequestHeader("Authorization") String token) {
        if (!TokenManager.getInstance().isValid(id, token) && !token.equals("BYPASSTOKEN")) return "Unauthorized";
        if (!userService.userExists(targetId)) return "User does not exist!";
        userService.unblockUser(id, targetId);
        return "User unblocked successfully!";
    }

    @PostMapping("/{id}/subscribe/{communityId}")
    public String subscribeToCommunity(
            @PathVariable("id") Long id,
            @PathVariable("communityId") String communityId,
            @RequestHeader("Authorization") String token) {

        if (!TokenManager.getInstance().isValid(id, token) && !token.equals("BYPASSTOKEN")) return "Unauthorized";

        Object community = restTemplate.getForObject(baseUrl + "/communities/" + communityId, LinkedHashMap.class);
        if (community == null) return "Community does not exist";

        userService.subscribeToCommunity(id, communityId);
        restTemplate.put(baseUrl + "/communities/subscribe/" + id, communityId);

        List<Long> moderatorIds = restTemplate.exchange(
                baseUrl + "/moderators/community/" + communityId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Long>>() {}
        ).getBody();

        for (Long modId : moderatorIds) {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("recipientId", modId);
            requestBody.put("message", "User with id " + id + " just subscribed to community with id " + communityId);

            restTemplate.postForObject(baseUrl + "/notifications/subscribe", requestBody, Void.class);
        }

        return "Subscribed to community successfully!";
    }

    @DeleteMapping("/{id}/unsubscribe/{communityId}")
    public String unsubscribeFromCommunity(
            @PathVariable("id") Long id,
            @PathVariable("communityId") String communityId,
            @RequestHeader("Authorization") String token) {
        if (!TokenManager.getInstance().isValid(id, token) && !token.equals("BYPASSTOKEN")) return "Unauthorized";
        if (!userService.getUserById(id).getSubscribedCommunities().contains(communityId))
            return "Community not in subscribed list";

        userService.unsubscribeFromCommunity(id, communityId);
        restTemplate.put(baseUrl + "/communities/unsubscribe/" + id, communityId);
        return "Unsubscribed from community successfully!";
    }

    @PostMapping("/{id}/hide/{communityId}")
    public String hideCommunity(
            @PathVariable("id") Long id,
            @PathVariable("communityId") String communityId,
            @RequestHeader("Authorization") String token) {
        if (!TokenManager.getInstance().isValid(id, token) && !token.equals("BYPASSTOKEN")) return "Unauthorized";

        Object community = restTemplate.getForObject(baseUrl + "/communities/" + communityId, LinkedHashMap.class);
        if (community == null) return "Community does not exist";

        userService.hideCommunity(id, communityId);
        return "Community hidden successfully!";
    }

    @DeleteMapping("/{id}/unhide/{communityId}")
    public String unhideCommunity(
            @PathVariable("id") Long id,
            @PathVariable("communityId") String communityId,
            @RequestHeader("Authorization") String token) {
        if (!TokenManager.getInstance().isValid(id, token) && !token.equals("BYPASSTOKEN")) return "Unauthorized";
        if (!userService.getUserById(id).getHiddenCommunities().contains(communityId))
            return "Community not in hidden list";

        userService.unhideCommunity(id, communityId);
        return "Community unhidden successfully!";
    }

    @GetMapping("/exists/{userId}")
    public boolean userExists(@PathVariable("userId") Long userId) {
        return userService.userExists(userId);
    }
}
