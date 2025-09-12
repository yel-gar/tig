package ru.vsu.cs.garanzha.tig.managers;

import com.google.gson.Gson;
import ru.vsu.cs.garanzha.tig.TigFile;

import java.io.*;
import java.util.Scanner;

public class MetaFileManager {
    private static final Gson GSON = new Gson();

    public static class TigFileJSONModel {
        public int currentVersion;
        public int maxVersion;

        public TigFileJSONModel(int currentVersion, int maxVersion) {
            this.currentVersion = currentVersion;
            this.maxVersion = maxVersion;
        }
    }

    public static void saveMetaFile(TigFile file) {
        var data = new TigFileJSONModel(file.getCurrentVersion(), file.getMaxVersion());
        var json = GSON.toJson(data);

        var metaFile = DataManager.getMetaFile(file.getPath());
        try (
            var writer = new FileWriter(metaFile)){
            writer.write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);  // TODO add handling
        }
    }

    public static TigFileJSONModel loadMetaFile(TigFile file) {
        var metaFile = DataManager.getMetaFile(file.getPath());
        try {
            var scanner = new Scanner(metaFile);
            var sb = new StringBuilder();
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine());
            }

            return GSON.fromJson(sb.toString(), TigFileJSONModel.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);  // TODO
        }
    }
}
