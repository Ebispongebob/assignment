package org.example.memstore.common;

public interface Constants {
    String HELP = "[OPERATION][KEY][VALUE] \n"
            + "-- [OPERATION] \n"
            + "SET: set value \n"
            + "GET: get value \n"
            + "DEL: delete key and value";

    String OK = "%s ok 1";

    String GET_ERROR = "%s not exist";
}