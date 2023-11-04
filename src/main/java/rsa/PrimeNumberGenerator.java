package rsa;

import java.math.BigInteger;
import java.util.Random;


public class PrimeNumberGenerator {
    private static final BigInteger THREE = new BigInteger("3");

    public static BigInteger generatePrimeNumber(int bitLength) {
        Random random = new Random();
        BigInteger candidate;
        do {
            candidate = new BigInteger(bitLength, random);
        } while (!isPrime(candidate, 5));
        return candidate;
    }

    // Miller-Rabin primality test
    public static boolean isPrime(BigInteger n, int iterations) {
        if (n.compareTo(BigInteger.ONE) <= 0) {
            return false;
        }
        if (n.compareTo(THREE) <= 0) {
            return true;
        }
        if (n.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            return false;
        }
        for (int i = 0; i < iterations; i++) {
            if (!millerRabinTest(n)) {
                return false;
            }
        }

        return true;
    }

    // Miller-Rabin primality test for a single iteration
    public static boolean millerRabinTest(BigInteger n) {
        BigInteger s = n.subtract(BigInteger.ONE);
        int t = 0;
        while (s.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            s = s.divide(BigInteger.TWO);
            t++;
        }
        Random random = new Random();
        BigInteger a = new BigInteger(n.bitLength(), random);
        a = a.mod(n.subtract(BigInteger.TWO)).add(BigInteger.TWO);
        BigInteger x = a.modPow(s, n);
        if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE))) {
            return true;
        }
        for (int i = 1; i < t; i++) {
            x = x.modPow(BigInteger.TWO, n);
            if (x.equals(BigInteger.ONE)) {
                return false;
            }
            if (x.equals(n.subtract(BigInteger.ONE))) {
                return true;
            }
        }
        return false;
    }
}