package org.example.memstore.server;

import org.example.memstore.server.mem.MemoryDatabase;

import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

public class MemServer implements Closeable {

    private DatagramSocket  ds;
    private MemoryDatabase  md;
    private ExecutorService executorService;

    /**
     * @param pall thread number
     * @param port listening port
     */
    public MemServer(int pall, int port) throws SocketException {
        init(pall, port);
    }

    public void run() throws IOException, ExecutionException, InterruptedException {
        listen();
    }

    private void init(int pall, int port) throws SocketException {
        // init socket
        ds = new DatagramSocket(port);

        // init db
        md = MemoryDatabase.MEMORY_DATABASE;

        // init executor
        executorService = Executors.newFixedThreadPool(pall);
    }

    private void listen() throws IOException, ExecutionException, InterruptedException {
        while (true) {
            // data buffer
            byte[]         buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            // receive packet
            ds.receive(packet);

            // convert to String
            String command = new String(packet.getData(), packet.getOffset(), packet.getLength(), StandardCharsets.UTF_8);
            System.out.println("input command:" + command);

            // submit to executor
            String result = submit0(command);

            // callback
            byte[] data = result.getBytes(StandardCharsets.UTF_8);
            packet.setData(data);
            ds.send(packet);
        }
    }

    private String submit0(String commandLine) throws ExecutionException, InterruptedException {
        // submit command
        Future<String> submit = executorService.submit(() -> {
            System.out.println(Thread.currentThread().getName() + "is executing");
            return md.operationDatabase(commandLine);
        });
        return submit.get();
    }

    @Override
    public void close() {
        try {
            ds.close();
            System.out.println("socket closed");
        } catch (Exception e) {
            System.out.println("socket closed failed");
            e.printStackTrace();
        }
    }
}
