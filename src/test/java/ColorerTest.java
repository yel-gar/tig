import org.junit.jupiter.api.Test;
import ru.vsu.cs.garanzha.tig.colorer.Color;
import ru.vsu.cs.garanzha.tig.colorer.Colorer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ColorerTest {
    @Test
    void testColored() {
        String coloredRed = Colorer.colored("test red", Color.RED);
        String coloredGreen = Colorer.colored("test green", Color.GREEN);
        assertEquals("\u001B[31mtest red\u001B[0m", coloredRed);
        assertEquals("\u001B[32mtest green\u001B[0m", coloredGreen);
    }

    @Test
    void testTagColored() {
        String original = "hi, this should be <c:green>green</> and this should be <c:red>red</>";
        String expected = "hi, this should be \u001B[32mgreen\u001B[0m and this should be \u001B[31mred\u001B[0m";
        String colored = Colorer.tagColored(original);
        assertEquals(expected, colored);
    }
}
