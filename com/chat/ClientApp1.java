package com.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientApp {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8888);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                try {
                    while (true) {
                        String message = in.readUTF();
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    throw new RuntimeException("SWW", e);
                }
            }).start();

            Scanner scanner = new Scanner(System.in);
            while (true) {
                try {
                    System.out.println("...");
                    out.writeUTF(scanner.nextLine());
                } catch (IOException e) {
                    throw new RuntimeException("SWW", e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
