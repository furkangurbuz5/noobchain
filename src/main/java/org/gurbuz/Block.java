package org.gurbuz;

import java.util.ArrayList;
import java.util.Date;

public class Block {


    public String hash;
    public String previousHash;
    public String merkleRoot;
    public ArrayList<Transaction> transactions = new ArrayList<>();
    private long timeStamp;
    private int nonce;

    public Block(String previousHash){
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }

    public String calculateHash()
    {
        return StringUtil.applySha256(
                this.previousHash +
                        Long.toString(this.timeStamp) +
                        Integer.toString(this.nonce) +
                        this.merkleRoot
                );
    }

    public void mineBlock(int difficulty)
    {
        this.merkleRoot = StringUtil.getMerkleRoot(transactions);
        String target = StringUtil.getDifficultyString(difficulty);
        while(!this.hash.substring(0, difficulty).equals(target)){
            this.nonce++;
            this.hash = calculateHash();
        }
        System.out.println("Block mined!!! : " + this.hash);
    }

    public boolean addTransaction(Transaction transaction)
    {
        if(transaction == null) return false;
        if((previousHash != "0")){
            if((!transaction.processTransaction())){
                System.out.println("Transaction failed to process");
                return false;
            }
        }
        transactions.add(transaction);
        System.out.println("Transaction successful");
        return true;
    }
}










