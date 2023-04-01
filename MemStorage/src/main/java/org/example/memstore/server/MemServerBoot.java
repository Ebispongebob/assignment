package org.example.memstore.server;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class MemServerBoot {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        try (MemServer memServer = new MemServer(3, 9999)) {
            System.out.println("------ server running");
            memServer.run();
        }
    }
}
