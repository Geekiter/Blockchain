package blockchain;

import java.security.*;

public class SignMessage {
    public byte[] sign(String data, PrivateKey privateKey) {
        try {
            Signature rsa = Signature.getInstance("SHA1withRSA");
            try {
                rsa.initSign(privateKey);
                try {
                    rsa.update(data.getBytes());
                    return rsa.sign();
                } catch (SignatureException e) {
                    e.printStackTrace();
                }
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
