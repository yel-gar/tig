package ru.vsu.cs.garanzha.tig.managers;

import java.io.File;
import java.nio.file.Paths;
import java.util.Base64;

public class DataManager {
    public static final String DATA_DIR = "./.tig";

    public static void checkDirectory() {
        var file = new File(DATA_DIR);
        if (!file.exists()) {
            var result = file.mkdir();

            if (!result) {
                throw new RuntimeException("Failed to create directory " + DATA_DIR);
            }
        }
    }

    private static String getTigFilename(String filepath) {
        // unify path to ensure valid b64 encode based on relative to current dir
        filepath = Paths.get(filepath).normalize().toString();
        return Base64.getEncoder().encodeToString(filepath.getBytes());
    }

    public static File getDataFile(String filepath, int version) {
        var b64Name = DataManager.getTigFilename(filepath);
        return new File(DATA_DIR + "/" + String.format("%s-%d.tigdata", b64Name, version));
    }

    public static File getMetaFile(String filepath) {
        var b64Name = DataManager.getTigFilename(filepath);
        return new File(DATA_DIR + "/" + String.format("%s.json", b64Name));
    }
}
