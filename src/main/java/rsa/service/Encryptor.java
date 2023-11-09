package rsa.service;

import rsa.domain.Key;

public interface Encryptor <T> {
    T encrypt(T input, Key publicKey);
}
