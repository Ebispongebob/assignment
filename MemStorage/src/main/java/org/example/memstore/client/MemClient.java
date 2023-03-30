package org.example.memstore.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class MemClient {
    public static void main(String[] args) throws IOException {
        try (DatagramSocket ds = new DatagramSocket()) {
            Scanner sc = new Scanner(System.in);
            while (sc.hasNextLine()) {
                String commandLine = sc.nextLine();
                ds.setSoTimeout(1000);
                ds.connect(InetAddress.getByName("localhost"), 9999); // 连接指定服务器和端口
                // 发送:
                byte[]         data   = commandLine.getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length);
                ds.send(packet);
                // 接收:
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
}
