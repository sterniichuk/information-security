package rsa.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DESCipherTest {

    @Test
    void encrypt() {
        DESCipher c = new DESCipher();
        var key = "12345678";
        String input = "Hell0";
        byte[] encryptedInput = c.encrypt(input.getBytes(), key);
        var decrypted = new String(c.decrypt(encryptedInput, key));
        assertEquals(input, decrypted);
    }
}