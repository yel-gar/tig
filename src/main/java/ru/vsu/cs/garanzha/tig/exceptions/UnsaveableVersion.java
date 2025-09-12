package ru.vsu.cs.garanzha.tig.exceptions;

public class UnsaveableVersion extends TigException {
    public UnsaveableVersion(int version, int maxVersion) {
        super(String.format("You can only commit file if it is max version. Current version: %d | Max version: %d", version, maxVersion));
    }
}
