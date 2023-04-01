package org.example.memstore.server.parser;

import org.example.memstore.common.GrammarException;
import org.example.memstore.server.enums.Command;

public class KvParser implements Parser {

    @Override
    public String[] handle(String command) {
        try {
            // pre check param
            preCheck(command);

            // parse
            return doParse(command);

        } catch (GrammarException ge) {
            System.out.println("parse error, " + ge.getMessage());
            return null;
        }
    }

    private void preCheck(String command) {
        if (command.equalsIgnoreCase("--help")) {
            throw new GrammarException("get help");
        }

        if (command == null || command.isEmpty()) {
            throw new GrammarException("command could not be null or empty");
        }

        String[] kv = command.split(" ");
        if (kv.length < 1) {
            throw new GrammarException("command parse failed");
        }

        // check operation
        String o          = kv[0];
        Command operation = null;
        for (Command value : Command.values()) {
            if (value.getName().equalsIgnoreCase(o)) {
                operation = value;
                break;
            }
        }
        if (operation == null) {
            throw new GrammarException("command key is not valid");
        }

        // check key, value
        switch (operation) {
        case GET:
        case DEL:
            if (kv.length != 2) {
                throw new GrammarException("command not found");
            }
            break;
        case SET:
            if (kv.length != 3) {
                throw new GrammarException("command not found");
            }
            break;
        default:
        }
    }

    private String[] doParse(String kv) {
        return kv.split(" ");
    }
}
