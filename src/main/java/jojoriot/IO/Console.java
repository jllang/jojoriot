package jojoriot.IO;

import java.util.Scanner;

/**
 *
 * @author riku
 */
public class Console implements IO {
    private Scanner scanner = new Scanner(System.in);
    
    @Override
    public void print(String string) {
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
