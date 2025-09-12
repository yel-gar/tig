package ru.vsu.cs.garanzha.tig.colorer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Colorer {
    private static final Pattern tagPattern = Pattern.compile("<c:(.+)>");

    public static String colored(String text, Color color) {
        return color.getValue() + text + Color.RESET.getValue();
    }

    public static String tagColored(String text) {
        text = text.replaceAll("</>", Color.RESET.getValue());
        var buffer = new StringBuilder();
        var matcher = tagPattern.matcher(text);
        while (matcher.find()) {
            var name = matcher.group(1);
            matcher.appendReplacement(buffer, Matcher.quoteReplacement(Color.getByName(name).getValue()));
        }
        matcher.appendTail(buffer);

        return buffer.toString();
    }
}
