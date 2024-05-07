package org.Artemis.core.crypto;

public class Transaction {
    private String sender;
    private String recipient;
    private double amount;
    private long timestamp;

    public Transaction(String sender, String recipient, double amount, long timestamp) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String calculateHash() {
        return Integer.toString((sender + recipient + amount + timestamp).hashCode());
    }

    public String toString() {
        return "Transaction{" +
                "sender='" + sender + '\'' +
                ", recipient='" + recipient + '\'' +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                '}';
    }
}
