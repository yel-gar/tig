package ru.vsu.cs.garanzha.tig.colorer;

public enum Color {
    // ANSI color codes
    RESET("\u001B[0m"),
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m"),

    // Clear screen
    CLEAR_SCREEN("\033[H\033[2J");

    private final String value;

    Color(String value) {
        this.value = value;
    }

    public static Color getByName(String name) {
        return valueOf(name.toUpperCase());
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
