package org.example;

import org.example.server.Server;

import java.io.IOException;

public class Main {
    private static final int SERVER_PORT = 5050;

    public static void main(String[] args) {
        try {
            Server s = Server.buildServer(SERVER_PORT);
            s.start();
        } catch (IOException e) {
            System.out.println("Failed to start");
        }
    }
}
