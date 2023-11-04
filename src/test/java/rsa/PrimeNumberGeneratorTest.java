package rsa;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class PrimeNumberGeneratorTest {
    private static final String primeNumberAsString = "77945873542698044292370372353138231581146878274169";
    private static final BigInteger prime = new BigInteger(primeNumberAsString);
    private static final BigInteger notBigPrime = prime.multiply(prime);

    @Test
    void isPrime() {
        boolean probablePrime = prime.isProbablePrime(1);
        assertTrue(probablePrime);
        assertTrue(PrimeNumberGenerator.isPrime(prime, 1));
    }
    @Test
    void isNotPrime() {
        BigInteger notPrime = new BigInteger(primeNumberAsString + "0");
        boolean probablePrime = notPrime.isProbablePrime(1);
        assertFalse(probablePrime);
        assertFalse(PrimeNumberGenerator.isPrime(notPrime, 1));
    }

    @Test
    void isNotBigPrime() {
        boolean probablePrime = notBigPrime.isProbablePrime(1);
        assertFalse(probablePrime);
        assertFalse(PrimeNumberGenerator.isPrime(notBigPrime, 1));
    }
}