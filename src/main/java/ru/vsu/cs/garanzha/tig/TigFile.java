package ru.vsu.cs.garanzha.tig;

import ru.vsu.cs.garanzha.tig.exceptions.BadFileException;
import ru.vsu.cs.garanzha.tig.exceptions.BadVersion;
import ru.vsu.cs.garanzha.tig.exceptions.UnsaveableVersion;
import ru.vsu.cs.garanzha.tig.managers.HistoryFileManager;
import ru.vsu.cs.garanzha.tig.managers.MetaFileManager;

import java.io.*;

public class TigFile extends File {
    private int currentVersion;
    private int maxVersion;

    public TigFile(String pathname) {
        super(pathname);
        currentVersion = 0;
        maxVersion = 0;
    }

    public TigFile(String pathname, int currentVersion, int maxVersion) {
        super(pathname);
        this.currentVersion = currentVersion;
        this.maxVersion = maxVersion;
    }

    public void goToVersion(int version) throws BadVersion, BadFileException {
        loadVersion(version);
        saveMeta();  // after version is loaded we have to update saved metadata
    }

    public void commit() throws UnsaveableVersion, BadFileException {
        if (currentVersion != maxVersion) {
            throw new UnsaveableVersion(currentVersion, maxVersion);
        }

        currentVersion++;
        maxVersion++;

        saveVersion();
        saveMeta();
    }

    public void loadMeta() {
        var metadata = MetaFileManager.loadMetaFile(this);
        this.currentVersion = metadata.currentVersion;
        this.maxVersion = metadata.maxVersion;
    }

    public void saveMeta() {
        MetaFileManager.saveMetaFile(this);
    }

    private void loadVersion(int version) throws BadVersion, BadFileException {
        if (version > maxVersion || version < 1) {
            throw new BadVersion(maxVersion);
        }

        // TODO this will remove current file version unless it is committed
        var data = HistoryFileManager.getFileData(this.getPath(), version);
        try (var fos = new FileOutputStream(this)) {
            fos.write(data);
            currentVersion = version;
        } catch (IOException e) {
            throw new BadFileException(this.getPath());
        }
    }

    private void saveVersion() throws BadFileException {
        try (var fis = new FileInputStream(this)) {
            HistoryFileManager.writeFileData(this.getPath(), currentVersion, fis.readAllBytes());
        } catch (IOException e) {
            throw new BadFileException(this.getPath());
        }
    }

    public int getCurrentVersion() {
        return this.currentVersion;
    }

    public int getMaxVersion() {
        return this.maxVersion;
    }
}
