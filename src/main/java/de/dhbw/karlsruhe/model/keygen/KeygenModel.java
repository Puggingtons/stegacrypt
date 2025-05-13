package de.dhbw.karlsruhe.model.keygen;

import de.dhbw.karlsruhe.util.observers.ChangeObserver;

import java.io.File;
import java.util.function.Consumer;

public class KeygenModel {
    private final ChangeObserver<String> keyName;
    private final ChangeObserver<File> saveDirectory;

    public KeygenModel() {
        this.keyName = new ChangeObserver<>();
        this.saveDirectory = new ChangeObserver<>();
    }

    public String getKeyName() {
        return keyName.get();
    }

    public void setKeyName(String keyName) {
        this.keyName.set(keyName);
    }

    public File getSaveFileDirectory() {
        return saveDirectory.get();
    }

    public void setSaveFileDirectory(File saveDirectory) {
        this.saveDirectory.set(saveDirectory);
    }

    public void onKeyNameChange(Consumer<String> consumer) {
        keyName.subscribe(consumer);
    }

    public void onSaveDirectoryChange(Consumer<File> consumer) {
        saveDirectory.subscribe(consumer);
    }
}
