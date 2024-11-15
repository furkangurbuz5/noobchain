package org.gurbuz;

import java.util.Date;

public class Block {


    public String hash;
    public String previousHash;
    public String data;
    private long timeStamp;
    private int nonce;

    public Block(String data, String previousHash){
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }

    public String calculateHash(){
        return StringUtil.applySha256(
                this.previousHash +
                        Long.toString(this.timeStamp) +
                        Integer.toString(this.nonce) +
                        this.data
                );
    }

    public void mineBlock(int difficulty){
        String target = new String(new char[difficulty]).replace('\0', '0');
        while(!this.hash.substring(0, difficulty).equals(target)){
            this.nonce++;
            this.hash = calculateHash();
        }
        System.out.println("Block mined!!! : " + this.hash);
    }

}
