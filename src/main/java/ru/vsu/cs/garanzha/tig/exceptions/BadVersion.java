package ru.vsu.cs.garanzha.tig.exceptions;

public class BadVersion extends TigException {
    public BadVersion(int maxVersion) {
        super(String.format("Version must be between 1 and %d", maxVersion));
    }
}
