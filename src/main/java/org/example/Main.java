package org.example;

import org.example.server.Server;

public class Main {
    public static void main(String[] args) {
        try {
            Server s = Server.buildServer(5050);
            s.start();
        }
        catch(Exception e){
            System.out.println("Failed to start server");
        }
    }
}
