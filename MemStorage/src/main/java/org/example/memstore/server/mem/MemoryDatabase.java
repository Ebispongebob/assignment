package org.example.memstore.server.mem;

import org.example.memstore.common.Constants;
import org.example.memstore.common.GrammarException;
import org.example.memstore.server.enums.Command;
import org.example.memstore.server.parser.KvParser;

public enum MemoryDatabase {

    // 0 ~ 15: save data; 16: default data
    MEMORY_DATABASE(new MemoryNameSpace[17], 0);

    private int current;

    private MemoryNameSpace[] database;

    MemoryDatabase(MemoryNameSpace[] database, int current) {
        this.database = database;
        this.current = current;
        for (int i = 0; i < database.length; i++) {
            database[i] = new MemoryNameSpace(String.valueOf(i));
        }
        initDatabase(current);
    }

    public MemoryNameSpace[] getDatabase() {
        return this.database;
    }

    public static MemoryDatabase getInstance() {
        return MEMORY_DATABASE;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
        this.initDatabase(current);
    }

    public String operationDatabase(String commandLine) {
        // preCheck and parse
        String[]        okv              = new KvParser().handle(commandLine);
        if (okv == null) {
            return Constants.HELP;
        }
        String          operation        = okv[0];
        MemoryNameSpace currentNamespace = database[current];
        String tips;

        // handle command
        switch (Command.getTarget(operation)) {
        case SET:
            if (currentNamespace.containsKey(okv[1])) {
                currentNamespace.replace(okv[1], okv[2]);
            } else {
                currentNamespace.put(okv[1], okv[2]);
            }
            tips = String.format(Constants.OK, "set");
            break;
        case GET:
            String res = database[current].get(okv[1]);
            if (res == null || res.isEmpty()) {
                tips = String.format(Constants.GET_ERROR, okv[1]);
            }else {
                tips = res;
            }
            break;
        case DEL:
            String del = database[current].remove(okv[1]);
            if (del == null || del.isEmpty()) {
                tips = String.format(Constants.GET_ERROR, okv[1]);
            }else {
                tips = String.format(Constants.OK, "del");
            }
            break;
        default:
            throw new GrammarException("operation not allowed");
        }
        return tips;
    }

    public boolean clearDatabase(int index) {
        if (index < 16 && index >= 0) {
            this.database[index] = new MemoryNameSpace(String.valueOf(index));
            return true;
        }
        return false;
    }

    private boolean initDatabase(int index) {
        if (this.database[index] != null) {
            return false;
        }
        if (index < 17 && index >= 0) {
            this.database[index] = new MemoryNameSpace(String.valueOf(index));
            return true;
        }
        return false;
    }
}
