package org.Artemis.core;

public class Miner {
    private Wallet wallet;
    private Blockchain blockchain;

    public Miner(Wallet wallet, Blockchain blockchain) {
        this.wallet = wallet;
        this.blockchain = blockchain;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Blockchain getBlockchain() {
        return blockchain;
    }

    public void setBlockchain(Blockchain blockchain) {
        this.blockchain = blockchain;
    }

    public void mine() {

    }

    @Override
    public String toString() {
        return "Miner{" +
                "wallet=" + wallet +
                ", blockchain=" + blockchain +
                '}';
    }
}
