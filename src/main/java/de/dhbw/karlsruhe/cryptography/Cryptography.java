package de.dhbw.karlsruhe.cryptography;

public abstract class Cryptography {
    @Override
    public abstract String toString();

    public abstract byte[] encrypt(byte[] data);

    public abstract byte[] decrypt(byte[] data);
}
