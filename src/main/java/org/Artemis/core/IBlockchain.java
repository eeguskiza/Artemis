package org.Artemis.core;

public interface IBlockchain {
    void addBlock(Block block);
    boolean verifyBlockchain();
    double getBalance(String address);
}
