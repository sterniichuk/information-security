package rsa.service;

import rsa.domain.Key;
import rsa.domain.RSAKeys;

import java.math.BigInteger;
import java.util.Random;

public class RSAKeyGenerator {
    private static final BigInteger publicExponent = new BigInteger("65537");

    public RSAKeys generateKeys(int bitLength) {
        Random rand = new Random();
        BigInteger p = BigInteger.probablePrime(bitLength, rand);
        BigInteger q = BigInteger.probablePrime(bitLength, rand);
        // Обчисліть модуль RSA (n)
        BigInteger n = p.multiply(q);
        // Обчислити повну функцію Ейлера (φ(n))
        //Функція Ейлера (φ(n)) підраховує натуральні числа, менші за n, які є взаємно простими з n.
        BigInteger phiN = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        // Виберіть таке ціле число e, щоб 1 < e < φ(n) і gcd(e, φ(n)) = 1
        BigInteger e = choosePublicExponent(phiN);
        // Обчислити e^-1 % phi
        BigInteger d = calculatePrivateExponent(e, phiN);
        // Public key — (e, n), private — (d, n)
        return new RSAKeys(new Key(e, n), new Key(d, n));
    }

    public static BigInteger choosePublicExponent(BigInteger phi) {
        if (isGoodExponent(phi, publicExponent)) {
            return publicExponent;
        }
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            BigInteger e = BigInteger.probablePrime(Math.max(8, phi.bitLength() / 2), rand);
            if (isGoodExponent(phi, e)) {
                return e;
            }
        }
        throw new IllegalArgumentException("Bad phi");
    }

    private static boolean isGoodExponent(BigInteger phi, BigInteger e) {
        return e.compareTo(phi) <= -1 && e.gcd(phi).equals(BigInteger.ONE);
    }

    public static BigInteger calculatePrivateExponent(BigInteger e, BigInteger phiN) {
        return e.modInverse(phiN);
    }
}

