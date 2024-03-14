package org.Artemis.core;

import java.util.List;

public class Block {
    private int index; // The index of the block in the blockchain
    private long timestamp; // The time at which the block was created
    private List<Transaction> transactions; // The list of transactions in the block
    private String prevHash; // The hash of the previous block in the blockchain
    private String hash; // The hash of the block
    private int nonce; // A nonce is a number that is used only once, in this case, it is used to generate the hash of the block

    public Block() {
        this.index = 0;
        this.timestamp = 0;
        this.transactions = null;
        this.prevHash = null;
        this.hash = null;
        this.nonce = 0;
    }

    public Block(int index, long timestamp, List<Transaction> transactions, String prevHash, String hash, int nonce) {
        this.index = index;
        this.timestamp = timestamp;
        this.transactions = transactions;
        this.prevHash = prevHash;
        this.hash = hash;
        this.nonce = nonce;
    }

    public int getIndex() {return index; }

    public void setIndex(int index) {
        this.index = index;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String getPrevHash() {
        return prevHash;
    }

    public void setPrevHash(String prevHash) {
        this.prevHash = prevHash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }

    @Override
    public String toString() {
        return "Block{" +
                "index=" + index +
                ", timestamp=" + timestamp +
                ", transactions=" + transactions +
                ", prevHash='" + prevHash + '\'' +
                ", hash='" + hash + '\'' +
                ", nonce=" + nonce +
                '}';
    }
}
