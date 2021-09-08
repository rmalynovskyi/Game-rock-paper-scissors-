import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

class KeyAndHMACGenerator {
    private static final String HMAC_SHA_256 = "HmacSHA256";
    private SecureRandom secureRandom;

    KeyAndHMACGenerator(SecureRandom secureRandom) {
        this.secureRandom = secureRandom;
    }

    String getKey(byte[] bytes) {
        this.secureRandom.nextBytes(bytes);
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    String getHMAC(byte[] bytes, String string) {
        Mac signer = null;
        try {
            signer = Mac.getInstance(HMAC_SHA_256);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        SecretKeySpec keySpec = new SecretKeySpec(bytes, HMAC_SHA_256);
        byte[] digest = null;
        try {
            if (signer != null) {
                signer.init(keySpec);
                digest = signer.doFinal(string.getBytes());
            }
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return getKey(digest);
    }
}
