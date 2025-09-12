package ru.vsu.cs.garanzha.tig;

import ru.vsu.cs.garanzha.tig.exceptions.*;

import java.util.InputMismatchException;

public class Controller {
    private TigFile currentFile;  // null = no file
    private final Prompter prompter;

    public Controller() {
        this.prompter = new Prompter();
    }

    private void mainLoop() throws InterruptedException {
        Command cmd;
        while (true) {
            cmd = prompter.promptCommand(currentFile);
            if (cmd == Command.QUIT) {
                break;
            }

            try {
                switch (cmd) {
                    case COMMIT -> commit();
                    case SELECT -> select();
                    case GOTO -> _goto();
                }
            } catch (TigException e) {
                throw new RuntimeException(e);  // TODO handling
            }
            Thread.sleep(2000);  // yes this is normal for TUI
        }
    }

    public void run() {
        try {
            mainLoop();
        } catch (InterruptedException e) {
            System.out.println("oops interrupted bye");
        } finally {
            prompter.close();
        }
    }

    public void select() throws TigException {
        var path = prompter.promptRelPath();
        var file = new TigFile(path);
        if (!file.exists()) {
            throw new FileNotFoundException(path);
        } else if (file.isDirectory()) {
            throw new BadFileException(path);
        }
        // TODO load file data from JSON here
        this.currentFile = file;
        prompter.printSuccess("File selected!");
    }

    public void commit() {
        if (currentFile == null) {
            throw new RuntimeException("I don't know how we got here but this is an impossible scenario");
        }
        try {
            currentFile.commit();
            prompter.printSuccess("File committed successfully");
        } catch (BadFileException | UnsaveableVersion e) {
            prompter.printException(e);
        }
    }

    public void _goto() {
        if (currentFile == null) {
            throw new RuntimeException("I don't know how we got here but this is an impossible scenario");
        }

        // automatic error handling while loop
        while (true) {
            try {
                var version = prompter.promptVersion();
                currentFile.goToVersion(version);
                prompter.printSuccess("Version changed!");
                return;
            } catch (InputMismatchException e) {
                prompter.printException("Aborted");
                return;
            } catch (BadFileException | BadVersion e) {
                prompter.printException(e);
            }
        }
    }
}
