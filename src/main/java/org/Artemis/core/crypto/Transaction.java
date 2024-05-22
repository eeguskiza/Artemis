package org.Artemis.core.crypto;

import java.util.Date;

public class Transaction {
    private String sender;
    private String receiver;
    private Name coin;
    private double amount;
    private Date timestamp;

    public Transaction(String sender, String receiver, Name coin, double amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.coin = coin;
        this.amount = amount;
        this.timestamp = new Date();
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public Name getCoin() {
        return coin;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", coin=" + coin +
                ", amount=" + amount +
                '}';
    }
}
