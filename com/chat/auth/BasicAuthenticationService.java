package com.chat.auth;

import com.chat.entity.User;

import java.util.List;
import java.util.Optional;

public class BasicAuthenticationService implements AuthenticationService {
    /**
     * Fake database with stubbed entities
     */
    private static final List<User> users;

    static {
        users = List.of(
                new User("n1", "1", "11"),
                new User("n2", "2", "22"),
                new User("n3", "3", "33")
        );
    }

    @Override
    public Optional<User> doAuth(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}
