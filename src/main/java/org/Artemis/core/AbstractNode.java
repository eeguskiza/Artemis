package org.Artemis.core;

abstract class AbstractNode {
    protected String address;
    protected int port;
    protected Blockchain blockchain;

    public AbstractNode(String address, int port) {
        this.address = address;
        this.port = port;
        this.blockchain = new Blockchain();
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public Blockchain getBlockchain() {
        return blockchain;
    }

    public void setBlockchain(Blockchain blockchain) {
        this.blockchain = blockchain;
    }

    public void addBlock(Block block) {
        blockchain.addBlock(block);
    }

    public boolean verifyBlockchain() {
        return blockchain.verifyBlockchain();
    }

    public double getBalance(String address) {
        return blockchain.getBalance(address);
    }
}
