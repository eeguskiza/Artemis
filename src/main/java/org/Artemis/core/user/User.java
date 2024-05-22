package org.Artemis.core.user;

import org.Artemis.core.crypto.Block;
import org.Artemis.core.crypto.Name;
import org.Artemis.core.crypto.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    private int count = 1;
    private int id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private String publicKey;
    private String privateKey;

    public User() {
        count++;
    }

    public User(String username, String password, String email, String firstName, String lastName, String role, String publicKey, String privateKey) {
        this.id = count + (int) (Math.random() * 1000);
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        count++;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public Map<Name, Double> calculateBalance(ArrayList<Block> blockchain) {
        Map<Name, Double> balance = new HashMap<>();

        for (Block block : blockchain) {
            for (Transaction transaction : block.getTransactions()) {
                if (transaction != null) {
                    if (transaction.getSender().equals(publicKey)) {
                        balance.put(transaction.getCoin(), balance.getOrDefault(transaction.getCoin(), 0.0) - transaction.getAmount());
                    }
                    if (transaction.getReceiver().equals(publicKey)) {
                        balance.put(transaction.getCoin(), balance.getOrDefault(transaction.getCoin(), 0.0) + transaction.getAmount());
                    }
                }
            }
        }

        return balance;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role='" + role + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", privateKey='" + privateKey + '\'' +
                '}';
    }
}
