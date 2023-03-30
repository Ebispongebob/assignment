package org.example.memstore.server.parser;

import org.example.memstore.common.GrammarException;
import org.example.memstore.server.enums.Command;

public class KvParser implements Parser {

    @Override
    public String[] parse(String command) {
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
        if (kv.length != 3) {
            throw new GrammarException("command parse failed");
        }
        String o = kv[0];

        boolean flag = false;
        for (Command value : Command.values()) {
            if (value.getName().equalsIgnoreCase(o)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            throw new GrammarException("command key is not valid");
        }
    }

    private String[] doParse(String kv) {
        return kv.split(" ");
    }
}