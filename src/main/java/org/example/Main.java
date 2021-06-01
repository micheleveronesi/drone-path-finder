package org.example;

import org.example.controller.Controller;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Controller c = Controller.buildController();
            c.start();
        } catch (IOException e) {
            System.out.println("Failed to start");
        }
    }
}
