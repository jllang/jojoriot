package jojoriot.UI;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import jojoriot.viitemanageri.Session;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

public class CLITest {

    /**
     * Virta, johon testattavan Main-luokan komentorivitulosteet ohjataan.
     */
    private static ByteArrayOutputStream out;
    private static CLI cli;
    // Myöhemmin voidaan ehkä tarvita myös tätä:
    // private static ByteArrayOutputStream virheulostulo;

    public CLITest() {
    }

    @BeforeClass
    public static void setupClass() {
        out = new ByteArrayOutputStream();
        // virheulostulo = new ByteArrayOutputStream();
    }

    public static void setupTest(final String input, final Session session) {
        cli = new CLI(new Scanner(input), new PrintStream(out), session);
    }

    /**
     * Uudelleenohjaa System.outin staattisessa kentässä <em>out
 </em> olevaan virtaan, jotta ohjelman komentorivitulosteita voidaan
     * testata.
     */
    private static void startCapture() {
        System.setOut(new PrintStream(out));
        // System.setErr(new PrintStream(virheulostulo));
    }

    /**
     * Poistaa System.outin uudelleenohjauksen.
     */
    private static void stopCapture() {
        System.setOut(null);
        // System.setErr(null);
    }

    /**
     * Tests that the exiting line is on the correct line of the output.
     */
    @Test
    public void pressing4OnMenuExits() {
        setupTest("4", new Session());
        startCapture();
        cli.start();
        String[] output = out.toString().split("\n");
        assertEquals("> Thank you for using Viitemanageri!", output[6]);
        stopCapture();
    }

}
