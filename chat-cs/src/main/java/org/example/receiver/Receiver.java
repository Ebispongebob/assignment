package org.example.receiver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

public class Receiver extends Thread {
    private final MulticastSocket multicastSocket;
    private final int             bufferSize;

    public Receiver(MulticastSocket multicastSocket, int bufferSize) {
        this.multicastSocket = multicastSocket;
        this.bufferSize = bufferSize;
    }

    public void run() {
        byte[]         buffer         = new byte[bufferSize];
        DatagramPacket datagramPacket = new DatagramPacket(buffer, bufferSize);
        // keep listening and print the message
        while (true) {
            try {
                multicastSocket.receive(datagramPacket);
                String message = new String(buffer, 0, datagramPacket.getLength());
                System.out.println(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
