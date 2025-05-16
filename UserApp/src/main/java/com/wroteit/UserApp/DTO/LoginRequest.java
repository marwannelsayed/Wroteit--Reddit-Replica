package com.wroteit.UserApp.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class LoginRequest {
    private final String username;
    private final String password;

    private LoginRequest(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
    }

    @JsonCreator
    public LoginRequest(
            @JsonProperty("username") String username,
            @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }

    public static class Builder {
        private String username;
        private String password;

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public LoginRequest build() {
            return new LoginRequest(this);
        }
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // equals, hashCode, toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginRequest that = (LoginRequest) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "username='" + username + '\'' +
                ", password='[PROTECTED]'" +
                '}';
    }
}