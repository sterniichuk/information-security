package des;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class DESCipher {
    private final Cipher cipher;

    public DESCipher() {
        try {
            cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] encrypt(byte[] input, String key) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey(key));
            return cipher.doFinal(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Key secretKey(String key) {
        if (key.length() < 8) {
            key = key + " ".repeat(8 - key.length());
        } else if (key.length() > 8) {
            key = key.substring(0, 8);
        }
        byte[] decodedKey = key.getBytes();
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "DES");
    }

    public byte[] decrypt(byte[] input, String key) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey(key));
            return cipher.doFinal(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
