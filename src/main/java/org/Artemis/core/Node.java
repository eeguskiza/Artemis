package org.Artemis.core;

import java.net.ServerSocket;
import java.util.List;

public class Node extends AbstractNode {
    private List<Node> peers;
    private ServerSocket serverSocket;

    public Node(String address, int port) {
        super(address, port);
    }

    public List<Node> getPeers() {
        return peers;
    }

    public void setPeers(List<Node> peers) {
        this.peers = peers;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
}
