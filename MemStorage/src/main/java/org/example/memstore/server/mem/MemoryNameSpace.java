package org.example.memstore.server.mem;

import java.util.concurrent.ConcurrentHashMap;

public class MemoryNameSpace extends ConcurrentHashMap<String, String> {
    private String name;

    public MemoryNameSpace(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
