
package jojoriot.IO;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 */
public final class FileIO implements IO {

    private final Scanner scanner;
    private final PrintWriter pw;

    public FileIO(final Scanner scanner, final PrintWriter pw) {
        this.scanner = scanner;
        this.pw = pw;
    }

    @Override
    public void print(final String string) throws IOException {
        pw.print(string);
    }

    @Override
    public int readInt() throws IOException {
        return Integer.parseInt(scanner.nextLine());
    }

    @Override
    public String readLine() throws IOException {
        return scanner.nextLine();
    }

}
