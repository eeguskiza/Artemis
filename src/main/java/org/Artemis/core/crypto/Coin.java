package org.Artemis.core.crypto;

import java.io.Serializable;

public class Coin implements Serializable {
    private String nombre;
    private Name name;
    //PoW o PoS
    private int year;
    private String symbol;
    private double price;
    private double totalSupply;
    private double circulatingSupply;

    Coin(){
        this.nombre = "Bitcoin";
        this.name = name.BTC;
        this.year = 2024;
        this.symbol = "\u20BF";
        this.price = 65000;
        this.totalSupply = 21000000;
        this.circulatingSupply = 19000000;
    }

    public Coin(String nombre, Name name, int year, String symbol, double price, double totalSupply, double circulatingSupply) {
        this.nombre = nombre;
        this.name = name;
        this.year = year;
        this.symbol = symbol;
        this.price = price;
        this.totalSupply = totalSupply;
        this.circulatingSupply = circulatingSupply;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalSupply() {
        return totalSupply;
    }

    public void setTotalSupply(double totalSupply) {
        this.totalSupply = totalSupply;
    }

    public double getCirculatingSupply() {
        return circulatingSupply;
    }

    public void setCirculatingSupply(double circulatingSupply) {
        this.circulatingSupply = circulatingSupply;
    }

    @Override
    public String toString() {
        return "Coin{" +
                "nombre='" + nombre + '\'' +
                ", name=" + name +
                ", year=" + year +
                ", symbol='" + symbol + '\'' +
                ", price=" + price + "€" +
                ", totalSupply=" + totalSupply +
                ", circulatingSupply=" + circulatingSupply +
                '}';
    }
}
