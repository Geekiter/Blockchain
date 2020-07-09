package blockchain;

import java.security.PublicKey;
import java.security.Signature;

public class VerifyMessage {

    public boolean verify(String data, byte[] signature, PublicKey publicKey) throws Exception {
        Signature sig = Signature.getInstance("SHA1withRSA");
        sig.initVerify(publicKey);
        sig.update(data.getBytes());
        return sig.verify(signature);
    }
}
