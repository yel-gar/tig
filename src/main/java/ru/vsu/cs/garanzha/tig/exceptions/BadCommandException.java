package ru.vsu.cs.garanzha.tig.exceptions;

public class BadCommandException extends TigException {
    public BadCommandException() {
        super("This is not a valid command");
    }
}
