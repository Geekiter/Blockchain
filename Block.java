package blockchain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block {

    private static MessageDigest digest;

    static {
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private final int minerId;
    private final int id;
    private final long timeStamp;
    private final String prevHash;
    private final long magicNumber;
    private final String hash;
    private Miner miner;
    public Block(Miner miner, int id, long timeStamp, long magicNumber, String prevHash) {
        this.miner = miner;
        this.minerId = miner.getId();
        this.id = id;
        this.timeStamp = timeStamp;
        this.magicNumber = magicNumber;
        this.hash = applySha256(id + timeStamp + magicNumber + prevHash);
        this.prevHash = prevHash;

    }

    public Miner getMiner() {
        return miner;
    }

    public int getId() {
        return id;
    }

    public String getPrevHash() {
        return prevHash;
    }

    public String getHash() {
        return hash;
    }

    public boolean isEquel(String zeroString) {
        return zeroString.equals(getHash().substring(0, zeroString.length()));
    }

    @Override
    public String toString() {
        return "Block:" + "\n" +
                "Created by miner # " + minerId + "\n" +
                "miner" + minerId + " gets 100 VC\n" +
                "Id: " + id + "\n" +
                "Timestamp: " + timeStamp + "\n" +
                "Magic number: " + magicNumber + "\n" +
                "Hash of the previous block:" + "\n" +
                prevHash + "\n" +
                "Hash of the block:" + "\n" +
                hash + "\n";
    }

    public String applySha256(String input) {
        try {
            /* Applies sha256 to our input */
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte elem : hash) {
                String hex = Integer.toHexString(0xff & elem);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}