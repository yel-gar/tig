package ru.vsu.cs.garanzha.tig.exceptions;

public class BadVersion extends TigException {
    public BadVersion(int version, int maxVersion) {
        super(String.format("Version %d is larger than maax version %d", version, maxVersion));
    }
}
