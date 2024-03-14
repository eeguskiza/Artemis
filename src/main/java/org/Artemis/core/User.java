package org.Artemis.core;

import java.util.List;

public class User {
    private String id;
    private Wallet wallet;
    private String username;
    private double balance;
    private List<Transaction> transactions;

    public User(String id, Wallet wallet, String username, double balance, List<Transaction> transactions) {
        this.id = id;
        this.wallet = wallet;
        this.username = username;
        this.balance = balance;
        this.transactions = transactions;
    }

    public String getId() {
        return id;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public String getUsername() {
        return username;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void removeTransaction(Transaction transaction) {
        transactions.remove(transaction);
    }

    public void updateBalance(double amount) {
        balance += amount;
    }

    public void updateWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public void updateUsername(String username) {
        this.username = username;
    }

    public void updateTransaction(Transaction transaction) {
        transactions.set(transactions.indexOf(transaction), transaction);
    }

    public void deleteTransaction(Transaction transaction) {
        transactions.remove(transaction);
    }

    public void deleteAllTransactions() {
        transactions.clear();
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", wallet=" + wallet +
                ", username='" + username + '\'' +
                ", balance=" + balance +
                ", transactions=" + transactions +
                '}';
    }
}
