package rsa.domain;

import java.math.BigInteger;

public record Key(BigInteger prime, BigInteger exponent) {
}
