package rsa.service;

import java.math.BigInteger;

public class RSAKeyGenerator {
    private static final BigInteger publicExponent = new BigInteger("65537");
    // Популярний вибір для загальнодоступної експоненти – 65537 (0x10001)


    public static void main(String[] args) {
        // Згенеруємо два різних простих числа
        BigInteger prime1 = PrimeNumberGenerator.generatePrimeNumber(512);
        BigInteger prime2 = PrimeNumberGenerator.generatePrimeNumber(512);
        // Обчисліть модуль RSA (n)
        BigInteger n = prime1.multiply(prime2);
        // Обчислити повну функцію Ейлера (φ(n))
        //Функція Ейлера (φ(n)) підраховує натуральні числа, менші за n, які є взаємно простими з n.
        BigInteger phiN = prime1.subtract(BigInteger.ONE).multiply(prime2.subtract(BigInteger.ONE));
        // Виберіть таке ціле число e, щоб 1 < e < φ(n) і gcd(e, φ(n)) = 1
        BigInteger e = choosePublicExponent(phiN);
        // Обчислити модульну мультиплікативну обернену e (d)
        BigInteger d = calculatePrivateExponent(e, phiN);
        // Відкритий ключ — (n, e), а закритий — (n, d)
        System.out.println("Public Key (n, e): (" + n + ", " + e + ")");
        System.out.println("Private Key (n, d): (" + n + ", " + d);
    }

    public static BigInteger choosePublicExponent(BigInteger phiN) {
        return publicExponent;
    }

    public static BigInteger calculatePrivateExponent(BigInteger e, BigInteger phiN) {
        // Обчисліть модульну мультиплікативну обернену e (d) за допомогою розширеного алгоритму Евкліда
        return e.modInverse(phiN);
    }
}

