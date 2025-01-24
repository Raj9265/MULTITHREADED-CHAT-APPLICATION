package com.client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientChat {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Connected to the chat server.");
            Scanner scanner = new Scanner(System.in);

            
            Thread readerThread = new Thread(() -> {
                String serverMessage;
                try {
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println("Server: " + serverMessage);
                    }
                } catch (IOException e) {
                    System.out.println("Connection to server lost.");
                }
            });

            readerThread.start();

            while (scanner.hasNextLine()) {
                String clientMessage = scanner.nextLine();
                out.println(clientMessage);
                if ("exit".equalsIgnoreCase(clientMessage)) {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
    }
}

