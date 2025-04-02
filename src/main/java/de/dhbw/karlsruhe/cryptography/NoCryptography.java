package de.dhbw.karlsruhe.cryptography;

public class NoCryptography extends Cryptography {
    @Override
    public String toString() {
        return "No Cryptography";
    }

    @Override
    public byte[] encrypt(byte[] data) {
        return data;
    }

    public byte[] decrypt(byte[] data) {
        return data;
    }
}
