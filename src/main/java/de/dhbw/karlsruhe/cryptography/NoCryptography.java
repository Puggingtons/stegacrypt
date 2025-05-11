package de.dhbw.karlsruhe.cryptography;

import java.security.Key;

public class NoCryptography extends Cryptography {
    @Override
    public String toString() {
        return "No Cryptography";
    }

    @Override
    public byte[] encrypt(byte[] data, Key key) {
        return data;
    }

    public byte[] decrypt(byte[] data, Key key) {
        return data;
    }
}
