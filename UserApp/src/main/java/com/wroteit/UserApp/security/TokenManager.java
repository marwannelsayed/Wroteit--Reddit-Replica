package com.wroteit.UserApp.security;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TokenManager {
    private static final TokenManager instance = new TokenManager();
    private final Map<Long, Session> sessions = new ConcurrentHashMap<>();
    private static final long TIMEOUT = 2 * 60 * 1000; // 2 minutes

    private static class Session {
        String token;
        long timestamp;
        Session(String token) {
            this.token = token;
            this.timestamp = System.currentTimeMillis();
        }
    }

    private TokenManager() {}

    public static TokenManager getInstance() {
        return instance;
    }

    public String generateToken(Long userId) {
        String token = UUID.randomUUID().toString();
        sessions.put(userId, new Session(token));
        return token;
    }

    public boolean isValid(Long userId, String token) {
        Session session = sessions.get(userId);
        if (session == null || !session.token.equals(token)) return false;
        if (System.currentTimeMillis() - session.timestamp > TIMEOUT) {
            sessions.remove(userId);
            return false;
        }
        session.timestamp = System.currentTimeMillis(); // reset timeout on use
        return true;
    }

    public void invalidateToken(Long userId) {
        sessions.remove(userId);
    }
}

