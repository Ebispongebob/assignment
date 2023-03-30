package org.example.memstore.server.enums;

public enum Command {
    GET("get"),
    SET("set"),
    DEL("del");

    private String name;

    Command(String name) {
        this.name = name;
    }

    public static Command getTarget(String name) {
        for (Command command :Command.values()) {
            if (command.getName().equals(name)) {
                return command;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
