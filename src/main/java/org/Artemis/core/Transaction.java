package org.Artemis.core;

public class Transaction {
    private String transactionId;
    private String sender;
    private String recipient;
    private double value;
    private String signature;

    public Transaction() {
        this.sender = null;
        this.recipient = null;
        this.value = 0;
        this.signature = null;
    }

    public Transaction(String sender, String recipient, double value, String signature) {
        this.sender = sender;
        this.recipient = recipient;
        this.value = value;
        this.signature = signature;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", sender='" + sender + '\'' +
                ", recipient='" + recipient + '\'' +
                ", value=" + value +
                ", signature='" + signature + '\'' +
                '}';
    }
}
