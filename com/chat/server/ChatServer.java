package com.chat.server;

import com.chat.auth.AuthenticationService;
import com.chat.auth.BasicAuthenticationService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ChatServer implements Server {
    private Set<ClientHandler> clients;
    private AuthenticationService authenticationService;
    private static final String broadcastNameAllUser = "all";

    public ChatServer() {
        try {
            System.out.println("Server is starting up...");
            ServerSocket serverSocket = new ServerSocket(8888);
            clients = new HashSet<>();
            authenticationService = new BasicAuthenticationService();
            System.out.println("Server is started up...");

            while (true) {
                System.out.println("Server is listening for clients...");
                Socket socket = serverSocket.accept();
                System.out.println("Client accepted: " + socket);
                new ClientHandler(this, socket);
                socket.setSoTimeout(120000);
            }
        } catch (IOException e) {
            throw new RuntimeException("SWW", e);
        }
    }

    @Override
    public synchronized void broadcastMessage(String message) {
        if(message.startsWith("-w")) {
            String[] credentialValues = message.split("\\s");
            if(broadcastNameAllUser.equals(credentialValues[1])){
                clients.forEach(client -> client.sendMessage(messageBuilder(credentialValues)));
            }
            for(ClientHandler client : clients){
                if(client.getName().equals(credentialValues[1])){


                    client.sendMessage(messageBuilder(credentialValues));
                }
            }
        }else{
            clients.forEach(client -> client.sendMessage(message));}
    }

    @Override
    public synchronized boolean isLoggedIn(String nickname) {
        return clients.stream()
                .filter(clientHandler -> clientHandler.getName().equals(nickname))
                .findFirst()
                .isPresent();
    }

    @Override
    public synchronized void subscribe(ClientHandler client) {
        clients.add(client);
    }

    @Override
    public synchronized void unsubscribe(ClientHandler client) {
        clients.remove(client);
    }

    @Override
    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public String messageBuilder(String[] cr){
        String messagForCurentUser = "";
        for(int i=2;i<cr.length;i++){
            messagForCurentUser = messagForCurentUser + cr[i];
        }
        return messagForCurentUser;
    }
}
