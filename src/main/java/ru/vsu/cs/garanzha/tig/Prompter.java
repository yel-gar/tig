package ru.vsu.cs.garanzha.tig;

import ru.vsu.cs.garanzha.tig.colorer.Color;
import ru.vsu.cs.garanzha.tig.colorer.Colorer;
import ru.vsu.cs.garanzha.tig.exceptions.TigException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Prompter {
    private final Scanner scanner;

    public Prompter() {
        this.scanner = new Scanner(System.in);
    }

    private void clearScreen() {
        System.out.println(Color.CLEAR_SCREEN.getValue());
        System.out.flush();
    }

    public Command promptCommand(TigFile file) {
        clearScreen();
        if (file == null) {
            System.out.printf(Colorer.tagColored("""
<c:green>[F]</> - Select file
<c:green>[Q]</> - Quit application
-------------------
Workdir: <c:cyan>%s</>
<c:red>No file selected!</>

<c:yellow>[?]</> Enter command:\s"""), System.getProperty("user.dir"));
            var cmd = scanner.nextLine().strip().toUpperCase();
            return switch (cmd) {
                case "F" -> Command.SELECT;
                case "Q" -> Command.QUIT;
                default -> null;
            };
        }
        System.out.printf(Colorer.tagColored("""
<c:green>[F]</> - Select new file
<c:green>[C]</> - Commit file version
<c:green>[G]</> - Go to version (0-%d)
<c:green>[Q]</> - Quit application
-------------------
Selected file: <c:green>%s</>
Version: <c:blue>%d</>
Last version: <c:blue>%d</>

<c:yellow>[?]</> Enter command:\s"""), file.getMaxVersion(), file.getPath(), file.getCurrentVersion(), file.getMaxVersion());
        String cmd = "";
        while (cmd.isEmpty()) {
            cmd = scanner.nextLine().strip().toUpperCase();
        }
        // FIXME for some reason sometimes scanner outputs empty command, this is fix attempt
        // maybe it's better to do nextChar
        return switch (cmd) {
            case "F" -> Command.SELECT;
            case "C" -> Command.COMMIT;
            case "G" -> Command.GOTO;
            case "Q" -> Command.QUIT;
            default -> null;
        };
    }

    public void printException(TigException exception) {
        printException(exception.getMessage());
    }

    public void printException(String text) {
        clearScreen();
        System.out.println(Colorer.colored("[!] ERROR : " + text, Color.RED));
    }

    public void printSuccess(String text) {
        clearScreen();
        System.out.println(Colorer.colored("[✓] ", Color.GREEN) + text);
    }

    public String promptRelPath() {
        clearScreen();
        System.out.print(Colorer.tagColored("<c:yellow>[?]</> Enter file relative path: "));
        return scanner.nextLine();
    }

    public int promptVersion() throws InputMismatchException {
        clearScreen();
        System.out.println(Colorer.colored("Warning: if unsaved, current file version will be lost. To abort, type non-integer.", Color.YELLOW));
        System.out.print(Colorer.tagColored("<c:yellow>[?]</> Enter file version: "));
        return scanner.nextInt();
    }

    public void close() {
        scanner.close();
    }
}
