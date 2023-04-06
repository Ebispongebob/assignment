package org.example.client;

import org.example.receiver.Receiver;
import org.example.sender.Sender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ChatClient {

    private final String addr;
    private final int    port;
    /**
     * default bufferSize
     */
    private       int    bufferSize = 1024;

    public ChatClient(String addr, int port) {
        this.addr = addr;
        this.port = port;
    }

    public ChatClient(String addr, int port, int bufferSize) {
        this.addr = addr;
        this.port = port;
        this.bufferSize = bufferSize;
    }

    public void run() {
        try {
            MulticastSocket multicastSocket = new MulticastSocket(port);
            InetAddress     multicastGroup  = InetAddress.getByName(addr);

            // join the chatroom
            multicastSocket.joinGroup(multicastGroup);

            System.out.println("please input the username: ");
            BufferedReader userNameReader = new BufferedReader(new InputStreamReader(System.in));
            String         userName       = userNameReader.readLine();

            // build sender and received
            Sender   sender   = new Sender(multicastSocket, multicastGroup, port, userName);
            Receiver receiver = new Receiver(multicastSocket, bufferSize);

            // run
            sender.start();
            receiver.start();

        } catch (UnknownHostException e) {
            System.err.println("Unknown Host Exception: " + e.getMessage());
        } catch (SocketException e) {
            System.err.println("Socket Exception: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IO Exception: " + e.getMessage());
        }
    }
}
