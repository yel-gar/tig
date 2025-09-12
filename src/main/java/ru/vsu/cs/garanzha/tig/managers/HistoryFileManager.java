package ru.vsu.cs.garanzha.tig.managers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class HistoryFileManager {
    public static byte[] getFileData(String filepath, int version) {
        var file = DataManager.getDataFile(filepath, version);
        if (!file.exists()) {
            return null;
        }

        try (
                var fis = new FileInputStream(file);
                var gis = new GZIPInputStream(fis)
        ) {
            return gis.readAllBytes();
        } catch (Exception e) {
            throw new RuntimeException(e);  // TODO handling
        }
    }

    public static void writeFileData(String filepath, int version, byte[] data) {
        DataManager.checkDirectory();
        var file = DataManager.getDataFile(filepath, version);
        try (
                var fos = new FileOutputStream(file);
                var gos = new GZIPOutputStream(fos)) {
            gos.write(data);
        } catch (Exception e) {
            throw new RuntimeException(e);  // TODO might wanna handle this better
        }
    }
}
