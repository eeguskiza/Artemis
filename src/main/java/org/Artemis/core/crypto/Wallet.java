package org.Artemis.core.crypto;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;

public class Wallet {
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public Wallet() {
        generateKeyPair();
    }

    private void generateKeyPair() {

        if (Security.getProvider("BC") == null) {
               Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        }

        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
            keyGen.initialize(ecSpec, new SecureRandom());
            KeyPair keyPair = keyGen.generateKeyPair();
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Transaction createTransaction(String recipient, double amount) {
        return new Transaction(encodeKey(publicKey), recipient, amount, System.currentTimeMillis());
    }

    // Codificar la clave pública para su uso en transacciones
    String encodeKey(PublicKey key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

}
