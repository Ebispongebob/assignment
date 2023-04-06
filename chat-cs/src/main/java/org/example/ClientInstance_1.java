package org.example;

import org.example.client.ChatClient;

public class ClientInstance_1 {
    public static void main(String[] args) {
        new ChatClient("225.255.255.1", 16789).run();
    }
}
