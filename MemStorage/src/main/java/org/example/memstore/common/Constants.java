package org.example.memstore.common;

public interface Constants {

    String APPLICATION = "application";
    String SERVER_IP = "server.ip";
    String SERVER_PORT = "server.port";

    String ACK = "ACK";

    String HELP = "[OPERATION][KEY][VALUE] \n"
            + "-- [OPERATION] \n"
            + "SET: set value \n"
            + "GET: get value";

    String OK = "%s ok 1";

    String GET_ERROR = "%s not exist";
}
