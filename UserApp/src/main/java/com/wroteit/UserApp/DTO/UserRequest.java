package com.wroteit.UserApp.DTO;

import java.util.Objects;

public class UserRequest {
    private final String username;
    private final String password;
    private final String email;

    private UserRequest(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.email = builder.email;
    }

    public static class Builder {
        private String username;
        private String password;
        private String email;

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public UserRequest build() {
            return new UserRequest(this);
        }
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    // equals, hashCode, toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRequest that = (UserRequest) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, email);
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "username='" + username + '\'' +
                ", password='[PROTECTED]'" +
                ", email='" + email + '\'' +
                '}';
    }
}