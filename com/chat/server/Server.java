package com.chat.server;

import com.chat.auth.AuthenticationService;

public interface Server {
    void broadcastMessage(String message);
    void userMessage(String message, String curentUser);
    boolean isLoggedIn(String nickname);
    void subscribe(ClientHandler client);
    void unsubscribe(ClientHandler client);
    AuthenticationService getAuthenticationService();
}
