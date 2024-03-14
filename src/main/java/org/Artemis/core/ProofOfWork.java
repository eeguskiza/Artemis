package org.Artemis.core;

public class ProofOfWork {
    private int difficulty;
    private String target;

    public ProofOfWork(int difficulty) {
        this.difficulty = difficulty;
        this.target = new String(new char[difficulty]).replace('\0', '0');
    }


}
