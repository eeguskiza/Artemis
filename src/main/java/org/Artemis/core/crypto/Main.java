package org.Artemis.core.crypto;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Block> blockchain = new ArrayList<>();
        Wallet walletA = new Wallet();
        Wallet walletB = new Wallet();

        // Crear transacciones
        Transaction t1 = walletA.createTransaction(walletB.encodeKey(walletB.getPublicKey()), 50.0);
        Transaction t2 = walletB.createTransaction(walletA.encodeKey(walletA.getPublicKey()), 30.0);

        // Agregar transacciones al bloque
        ArrayList<Transaction> block1Transactions = new ArrayList<>();
        block1Transactions.add(t1);
        block1Transactions.add(t2);

        // Crear bloque genesis
        Block genesisBlock = new Block(0, block1Transactions);
        blockchain.add(genesisBlock); // Agrega el bloque al blockchain

        // Imprimir detalles del bloque genesis
        System.out.println("Genesis Block Hash: " + genesisBlock.getBlockHash());
        System.out.println("Genesis Transactions:");
        for (Transaction tx : genesisBlock.getTransactions()) {
            System.out.println("  - " + tx.getSender() + " sent " + tx.getAmount() + " to " + tx.getRecipient() + " at " + tx.getTimestamp());
        }

        // Crear otro bloque después del genesis
        Transaction t3 = walletA.createTransaction(walletB.encodeKey(walletB.getPublicKey()), 20.0);
        ArrayList<Transaction> block2Transactions = new ArrayList<>();
        block2Transactions.add(t3);

        Block secondBlock = new Block(genesisBlock.getBlockHash(), block2Transactions);
        blockchain.add(secondBlock); // Agrega el nuevo bloque al blockchain

        // Imprimir detalles del segundo bloque
        System.out.println("Second Block Hash: " + secondBlock.getBlockHash());
        System.out.println("Second Block Transactions:");
        for (Transaction tx : secondBlock.getTransactions()) {
            System.out.println("  - " + tx.getSender() + " sent " + tx.getAmount() + " to " + tx.getRecipient() + " at " + tx.getTimestamp());
        }
    }
}
