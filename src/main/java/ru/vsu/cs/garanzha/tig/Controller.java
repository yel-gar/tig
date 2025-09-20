package ru.vsu.cs.garanzha.tig;

import ru.vsu.cs.garanzha.tig.exceptions.*;

import java.util.InputMismatchException;

public class Controller {
    private final Prompter prompter;
    private TigFile currentFile;  // null = no file
    private int waitDelay = 1000;

    public Controller() {
        this.prompter = new Prompter();
    }

    public void setWaitDelay(int newDelay) {
        if (newDelay < 0) {
            newDelay = 0;
        }
        waitDelay = newDelay;
    }

    private void mainLoop() throws InterruptedException {
        Command cmd;
        boolean running = true;
        while (running) {
            cmd = prompter.promptCommand(currentFile);
            try {
                switch (cmd) {
                    case null -> throw new BadCommandException();
                    case QUIT -> running = false;
                    case COMMIT -> commit();
                    case SELECT -> select();
                    case GOTO -> _goto();
                }
            } catch (TigException e) {
                prompter.printException(e);
            }
            Thread.sleep(waitDelay);  // yes this is normal for TUI
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
        this.currentFile = file;
        currentFile.loadMeta();
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
