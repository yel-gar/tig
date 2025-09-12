import org.junit.jupiter.api.Test;
import ru.vsu.cs.garanzha.tig.managers.DataManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilenameTest {
    @Test
    void testDatafileName() {
        String name = DataManager.getDataFile("./test", 1).getName();
        assertEquals("dGVzdA==-1.tigdata", name);
    }

    @Test
    void testMetafileName() {
        String name = DataManager.getMetaFile("./test").getName();
        assertEquals("dGVzdA==.json", name);
    }
}
