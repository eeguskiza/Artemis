package org.Artemis.core;

import java.util.List;

public class Blockchain implements  IBlockchain {
    private List<Block> chain;
    private int difficulty;
    private List<Transaction> pendingTransactions;

    @Override
    public void addBlock(Block block) {

    }

    @Override
    public boolean verifyBlockchain() {
        return false;
    }

    @Override
    public double getBalance(String address) {
        return 0;
    }
}
