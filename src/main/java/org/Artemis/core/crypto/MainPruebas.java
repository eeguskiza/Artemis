package org.Artemis.core.crypto;

import org.Artemis.core.user.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class MainPruebas {
    public static void main(String[] args) {
        // Crear blockchain
        ArrayList<Block> blockchain = new ArrayList<>();

        // Crear transacciones
        Transaction t1 = new Transaction("publicKey1", "publicKey2", Name.BTC, 0.5);
        Transaction t2 = new Transaction("publicKey2", "publicKey1", Name.ETH, 1.0);
        Transaction t3 = new Transaction("publicKey1", "publicKey2", Name.BTC, 0.2);
        Transaction t4 = new Transaction("publicKey3", "publicKey1", Name.ETH, 0.8);

        // Crear bloques y añadir transacciones
        Block block1 = new Block(new Date());
        block1.setTransaction(t1);
        block1.setTransaction(t2);

        Block block2 = new Block(new Date());
        block2.setTransaction(t3);
        block2.setTransaction(t4);

        // Añadir bloques a la blockchain
        blockchain.add(block1);
        blockchain.add(block2);

        // Crear usuario
        User user = new User();

        // Calcular balance del usuario
        Map<Name, Double> balance = user.calculateBalance(blockchain);

        // Mostrar balance
        System.out.println("Balance del usuario:");
        for (Map.Entry<Name, Double> entry : balance.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
