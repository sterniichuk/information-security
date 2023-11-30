package rsa.service;

import rsa.domain.Key;

import java.math.BigInteger;

public class RSACipher {
    public byte[] encrypt(byte[] input, Key publicKey){
        BigInteger msg = new BigInteger(input);
        return msg.modPow(publicKey.exponent(), publicKey.semiPrime()).toByteArray();
    }

    public byte[] decrypt(byte[] input, Key privateKey){
        return encrypt(input, privateKey);
    }
}
