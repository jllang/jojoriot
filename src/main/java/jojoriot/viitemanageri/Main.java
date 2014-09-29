package jojoriot.viitemanageri;
import java.io.PrintStream;
import java.util.Scanner;
import jojoriot.UI.CLI;

/**
 * The main class of this project that is responsible for creating a session and
 * ui and then starting them.
 */
public final class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        //final Console io = new Console();
        final Scanner in = new Scanner(System.in);
        final PrintStream out = new PrintStream(System.out);
        final Session session = new Session();
        final CLI ui = new CLI(in, out, session);

        ui.start();
    }
}
