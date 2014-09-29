package jojoriot.IO;

import java.util.Scanner;

/**
 *
 * @author riku
 */
public final class Console implements IO {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void print(final String string) {
        System.out.print(string);
    }

    @Override
    public int readInt() {
        return Integer.parseInt(scanner.nextLine());
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }
}
