package rsa.service;

import rsa.domain.Key;

public interface Decryptor<T> {
    T decrypt(T input, Key privateKey);
}
