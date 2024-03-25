package org.Artemis.core.user;

import java.util.HashMap;
import java.util.Map;

public class User {
    private static int count = 0;
    private int id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    //Constructor vacio
    public User() {
        this.id = ++count; // Incrementa el contador y asigna el ID
        this.username = "";
        this.password = "";
        this.email = "";
        this.firstName = "";
        this.lastName = "";
        this.role = "";
    }
    // Constructor
    public User(String username, String password, String email, String firstName, String lastName, String role) {
        this.id = ++count; // Incrementa el contador y asigna el ID
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRole() {
        return role;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Map<String, Double> getActivos() {
        Map<String, Double> activos = new HashMap<>();
        activos.put("Bitcoin", 12.5);
        activos.put("Ethereum", 8.0);
        activos.put("Cardano", 4.0);
        return activos;
    }

    // Método toString (opcional, pero útil para depuración)
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
