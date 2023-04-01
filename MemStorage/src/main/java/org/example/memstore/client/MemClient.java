package org.example.memstore.client;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class MemClient {
    public static void main(String[] args) throws IOException {
        autoSend();
    }

    private static void scannerSend() {
        try (DatagramSocket ds = new DatagramSocket()) {
            Scanner sc = new Scanner(System.in);
            while (sc.hasNextLine()) {
                String commandLine = sc.nextLine();
                ds.setSoTimeout(1000);
                ds.connect(InetAddress.getByName("localhost"), 9999);
                // send:
                byte[]         data   = commandLine.getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length);
                ds.send(packet);
                // rec:
                byte[] buffer = new byte[1024];
                packet = new DatagramPacket(buffer, buffer.length);
                ds.receive(packet);
                String resp = new String(packet.getData(), packet.getOffset(), packet.getLength());
                System.out.println(resp);
                ds.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void autoSend() throws IOException {
        DatagramSocket ds = new DatagramSocket();
        ds.setSoTimeout(1000);
        ds.connect(InetAddress.getByName("localhost"), 9999);
        byte[]         setData = "set k1 1".getBytes();
        byte[]         getData = "get k1".getBytes();
        DatagramPacket packet  = new DatagramPacket(setData, setData.length);
        ds.send(packet);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                // send:
                DatagramPacket packet2 = new DatagramPacket(getData, getData.length);
                try {
                    ds.send(packet2);
                    // rec:
                    byte[] buffer = new byte[1024];
                    packet2 = new DatagramPacket(buffer, buffer.length);
                    ds.receive(packet2);
                    String resp = new String(packet2.getData(), packet2.getOffset(), packet2.getLength());
                    System.out.println(Thread.currentThread().getName() + " get res: " + resp);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }).start();
        }

    }
}
