package de.dhbw.karlsruhe.cryptography;

import java.security.Key;

public abstract class Cryptography {
    @Override
    public abstract String toString();

    public abstract byte[] encrypt(byte[] data, Key key) throws Exception;

    public abstract byte[] decrypt(byte[] data, Key key) throws Exception;
}
