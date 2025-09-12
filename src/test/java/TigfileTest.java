import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import ru.vsu.cs.garanzha.tig.TigFile;
import ru.vsu.cs.garanzha.tig.exceptions.TigException;
import ru.vsu.cs.garanzha.tig.managers.DataManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TigfileTest {
    static final String TEST_FILENAME = "tigfile-test";

    @AfterAll
    static void cleanup() {
        new File(TEST_FILENAME).deleteOnExit();
        DataManager.getDataFile(TEST_FILENAME, 1).deleteOnExit();
        DataManager.getDataFile(TEST_FILENAME, 2).deleteOnExit();
        DataManager.getMetaFile(TEST_FILENAME).deleteOnExit();
    }

    @Test
    void testFileCommitGoto() throws TigException {
        var tigfile = new TigFile(TEST_FILENAME);
        writeToFile(tigfile, "test version 1");
        tigfile.commit();
        writeToFile(tigfile, "test version 2");
        tigfile.commit();

        tigfile.goToVersion(1);
        assertEquals("test version 1", readFromFile(tigfile));
        tigfile.goToVersion(2);
        assertEquals("test version 2", readFromFile(tigfile));
    }

    private void writeToFile(File file, String content) {
        try (var writer = new FileWriter(file)) {
            writer.write(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String readFromFile(File file) {
        try (var fis = new FileInputStream(file)) {
            return new String(fis.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
