package rsa.domain;

import java.io.Serializable;
import java.math.BigInteger;

public record Key(BigInteger exponent, BigInteger semiPrime) implements Serializable {
}
