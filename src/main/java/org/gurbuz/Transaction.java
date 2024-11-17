package org.gurbuz;

import java.security.*;
import java.util.ArrayList;

public class Transaction {

    public String transactionId; //hash
    public PublicKey sender;
    public PublicKey recipient;
    public float value;
    public byte[] signature;

    public ArrayList<TransactionInput> inputs = new ArrayList<>();
    public ArrayList<TransactionOutput> outputs = new ArrayList<>();

    private static int sequence = 0;

    public Transaction(
            PublicKey from,
            PublicKey to,
            float value,
            ArrayList<TransactionInput> inputs
    ){
        this.sender = from;
        this.recipient = to;
        this.value = value;
        this.inputs = inputs;
    }

    private String calculateHash()
    {
        sequence++;
        return StringUtil.applySha256(
            StringUtil.getStringFromKey(sender)+
                    StringUtil.getStringFromKey(recipient)+
                    Float.toString(value) + sequence
        );
    }

    public void generateSignature(PrivateKey privateKey)
    {
        String data = StringUtil.getStringFromKey(sender) +
                StringUtil.getStringFromKey(recipient) +
                Float.toString(value);
        signature = StringUtil.applyECDASig(privateKey, data);
    }

    public boolean verifySignature()
    {
        String data = StringUtil.getStringFromKey(sender)+
                StringUtil.getStringFromKey(recipient) +
                Float.toString(value);
        return StringUtil.verifyECDSASig(sender, data, signature);
    }

}











