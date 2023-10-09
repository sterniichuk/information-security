package rsa;

import domain.LargeNumber;

import java.util.Random;

public class PrimeNumberGenerator {
    public static LargeNumber generatePrimeNumber(int bitLength) {
        Random random = new Random();
        assert bitLength % 8 == 0;
        LargeNumber candidate;
        do {
            byte[] candidateBytes = new byte[bitLength / 8];
            random.nextBytes(candidateBytes);
            candidate = new LargeNumber(candidateBytes);


        } while (!isPrime(candidate));

        return candidate;
    }

    private static boolean isPrime(LargeNumber candidate) {
        return true;
    }
}

