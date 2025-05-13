package de.dhbw.karlsruhe.controller.keygen;

import de.dhbw.karlsruhe.cryptography.RSACryptography;
import de.dhbw.karlsruhe.model.keygen.KeygenModel;
import de.dhbw.karlsruhe.security.RSAKeyHandler;

import java.io.File;
import java.nio.file.Path;
import java.security.KeyPair;

public class KeygenController {

    private final KeygenModel model;

    public KeygenController(KeygenModel model) {
        this.model = model;
    }

    public void generateAndSaveKeyPair() {
        try {
            KeyPair pair = RSACryptography.generateKeyPair();

            Path path = model.getSaveFileDirectory().toPath();

            RSAKeyHandler.saveKeyToFile(pair.getPublic(), path.resolve(model.getKeyName() + ".pub").toFile());
            RSAKeyHandler.saveKeyToFile(pair.getPrivate(), path.resolve(model.getKeyName() + ".priv").toFile());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setKeyName(String keyName) {
        model.setKeyName(keyName);
    }

    public void setSaveFileDirectory(File saveFileDirectory) {
        model.setSaveFileDirectory(saveFileDirectory);
    }

}
