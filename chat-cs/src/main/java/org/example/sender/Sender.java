package org.example.sender;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Sender extends Thread {
    private final MulticastSocket multicastSocket;
    private final InetAddress multicastGroup;
    private final int    port;
    private final String userName;

    public Sender(MulticastSocket multicastSocket, InetAddress multicastGroup, int port, String userName) {
        this.multicastSocket = multicastSocket;
        this.multicastGroup = multicastGroup;
        this.port = port;
        this.userName = userName;
    }

    public void run() {
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        String message;
        try {
            // keep reading the keyboard input and sending message
            while (true) {
                message = userInput.readLine();
                String fullMessage = userName + ": " + message;
                byte[] buffer = fullMessage.getBytes();
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, multicastGroup, port);
                multicastSocket.send(datagramPacket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
