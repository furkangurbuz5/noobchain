package org.gurbuz;

import java.util.ArrayList;
import com.google.gson.GsonBuilder;

public class NoobChain {

    public static ArrayList<Block> blockchain = new ArrayList<>();
    public static final int DIFFICULTY = 3;

    public static void main(String[] args) {
        blockchain.add(new Block("Hi I am the first block", "Furkan"));
        System.out.println("Mining...");
        blockchain.get(0).mineBlock(DIFFICULTY);

        blockchain.add(new Block("Hi I am the second block", blockchain.get(blockchain.size()-1).hash));
        System.out.println("Mining...");
        blockchain.get(1).mineBlock(DIFFICULTY);

        blockchain.add(new Block("Hi I am the third block", blockchain.get(blockchain.size()-1).hash));
        System.out.println("Mining...");
        blockchain.get(2).mineBlock(DIFFICULTY);

        System.out.println("Checking if blockchain is valid : " + (isChainValid() ? "current blockchain is valid" : "current blockchain is not valid"));

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println(blockchainJson);



    }

    public static Boolean isChainValid(){
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[DIFFICULTY]).replace('\0', '0');

        for(int i = 1; i < blockchain.size(); i++){
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);
            if(!currentBlock.hash.equals(currentBlock.calculateHash())){
                System.out.println("Current hashes are not equal!");
                return false;
            }
            if(!previousBlock.hash.equals(currentBlock.previousHash)){
                System.out.println("Previous hashes are not equal!");
                return false;
            }

            if(!currentBlock.hash.substring(0, DIFFICULTY).equals(hashTarget)){
                System.out.println("This block hasn't been mined");
                return false;
            }
        }
        return true;
    }
}
