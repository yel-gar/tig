package ru.vsu.cs.garanzha.tig.exceptions;

public class FileNotFoundException extends TigException {
    public FileNotFoundException(String filename) {
        super(String.format("File `%s` does not exist", filename));
    }
}