package blockchain;

import java.security.*;

public class Key {
    private KeyPairGenerator keyGen;
    private KeyPair pair;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public Key(int keylength) throws NoSuchAlgorithmException{
        this.keyGen = KeyPairGenerator.getInstance("RSA");
        this.keyGen.initialize(keylength);
    }

    public void createKeys(){
        this.pair = this.keyGen.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }

    public PrivateKey getPrivateKey(){
        return this.privateKey;
    }
    public PublicKey getPublicKey(){
        return this.publicKey;
    }

}
