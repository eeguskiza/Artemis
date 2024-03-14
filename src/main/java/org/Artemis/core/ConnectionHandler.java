package org.Artemis.core;

import java.net.Socket;

public class ConnectionHandler {
    private Socket socket;
    private Node node;

    public ConnectionHandler(Socket socket, Node node) {
        this.socket = socket;
        this.node = node;
    }



}
