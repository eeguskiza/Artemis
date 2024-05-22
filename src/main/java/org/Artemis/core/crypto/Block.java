package org.Artemis.core.crypto;

import java.util.Arrays;
import java.util.Date;

public class Block {
    private static int count = 0;
    private int id;
    private Transaction[] transactions;
    private Date timestamp;
    private int transactionCount;

    public Block() {
        this.id = count;
        this.transactions = new Transaction[10];
        this.timestamp = new Date();
        this.transactionCount = 0;
        count++;
    }

    public Block(Date timestamp) {
        this.id = count;
        this.transactions = new Transaction[10];
        this.timestamp = timestamp;
        this.transactionCount = 0;
        count++;
    }

    public int getId() {
        return id;
    }

    public Transaction[] getTransactions() {
        return transactions;
    }

    public void setTransaction(Transaction transaction) {
        if (transactionCount < 10) {
            this.transactions[transactionCount] = transaction;
            transactionCount++;
        } else {
            System.out.println("Transaction list is full. Cannot add more transactions.");
        }
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Block{" +
                "id=" + id +
                ", transactions=" + Arrays.toString(transactions) +
                ", timestamp=" + timestamp +
                ", transactionCount=" + transactionCount +
                '}';
    }
}
