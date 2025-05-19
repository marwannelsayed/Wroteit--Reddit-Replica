package com.wroteit.UserApp.service;

import com.wroteit.UserApp.model.User;
import com.wroteit.UserApp.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import com.wroteit.UserApp.exception.UserNotFoundException;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        return userRepository.save(user);
    }

    public String login(Long id, String password) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!BCrypt.checkpw(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }


        return "Login successful";
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }



    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User updateBiography(Long id, String biography) {
        User user = getUserById(id);
        user.setBiography(biography);
        return userRepository.save(user);
    }

    public User followUser(Long userId, Long targetId) {
        User user = getUserById(userId);
        User target = getUserById(targetId);

        if (!user.getFollowing().contains(targetId)) {
            user.getFollowing().add(targetId);
        }

        if (!target.getFollowers().contains(userId)) {
            target.getFollowers().add(userId);
        }

        userRepository.save(target); // Save target FIRST
        return userRepository.save(user);
    }


    public User unfollowUser(Long userId, Long targetId) {
        User user = getUserById(userId);
        User target = getUserById(targetId);

        user.getFollowing().remove(targetId);
        target.getFollowers().remove(userId);

        userRepository.save(target); // Save target
        return userRepository.save(user); // Return updated user
    }


    public User blockUser(Long userId, Long targetId) {
        User user = getUserById(userId);

        if (!user.getBlockedUsers().contains(targetId)) {
            user.getBlockedUsers().add(targetId);
            user.getFollowing().remove(targetId);
        }

        return userRepository.save(user);
    }

    public User unblockUser(Long userId, Long targetId) {
        User user = getUserById(userId);
        user.getBlockedUsers().remove(targetId);
        return userRepository.save(user);
    }

    public User subscribeToCommunity(Long userId, String communityId) {
        User user = getUserById(userId);

        if (!user.getSubscribedCommunities().contains(communityId)) {
            user.getSubscribedCommunities().add(communityId);
            user.getHiddenCommunities().remove(communityId);
        }

        return userRepository.save(user);
    }

    public User unsubscribeFromCommunity(Long userId, String communityId) {
        User user = getUserById(userId);
        user.getSubscribedCommunities().remove(communityId);
        return userRepository.save(user);
    }

    public User hideCommunity(Long userId, String communityId) {
        User user = getUserById(userId);

        if (!user.getHiddenCommunities().contains(communityId)) {
            user.getHiddenCommunities().add(communityId);
            user.getSubscribedCommunities().remove(communityId);
        }

        return userRepository.save(user);
    }

    public User unhideCommunity(Long userId, String communityId) {
        User user = getUserById(userId);
        user.getHiddenCommunities().remove(communityId);
        return userRepository.save(user);
    }

    public boolean userExists(Long userId) {
        return userRepository.existsById(userId);
    }




}
