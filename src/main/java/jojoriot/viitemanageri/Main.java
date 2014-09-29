package jojoriot.viitemanageri;
import jojoriot.IO.Console;
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
        final Console io = new Console();
        final Session session = new Session();
        final CLI ui = new CLI(io, session);

        ui.start();
    }
}
