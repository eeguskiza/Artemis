package org.Artemis.core.crypto;

import java.util.ArrayList;
import java.util.Arrays;

public class Block {
    private int previousHash;
    private ArrayList<Transaction> transactions;
    private int blockHash;

    public Block(int previousHash, ArrayList<Transaction> transactions) {
        this.previousHash = previousHash;
        this.transactions = transactions;

        Object[] contents = {transactions.hashCode(), previousHash};
        this.blockHash = Arrays.hashCode(contents);
    }

    public int getPreviousHash() {
        return previousHash;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public int getBlockHash() {
        return blockHash;
    }


}
