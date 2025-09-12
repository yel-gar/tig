package ru.vsu.cs.garanzha.tig.exceptions;

public class BadFileException extends TigException {
    public BadFileException(String filename) {
        super(String.format("`%s` is not a file", filename));
    }
}
