package jojoriot.UI;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Scanner;
import jojoriot.references.Article;
import jojoriot.viitemanageri.Session;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CLITest {

    /**
     * Virta, johon testattavan Main-luokan komentorivitulosteet ohjataan.
     */
    private static ByteArrayOutputStream out;
    private static CLI cli;
    // private static ByteArrayOutputStream virheulostulo;

    public static void setupTest(final String input, final Session session) {
        out = new ByteArrayOutputStream();
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

    private Article getTestArticle() {
        LinkedHashMap<String, String> fields = new LinkedHashMap<>();
        fields.put("author", "Esko");
        fields.put("title", "Juttu");
        fields.put("journal", "Lehti");
        fields.put("year", "1666");
        Article article = new Article("esimerkki", fields);
        return article;
    }

    private Session getSessionWithArticle() {
        Session session = new Session();
        session.add(getTestArticle());
        return session;
    }

    /**
     * Tests that the exiting line is on the correct line of the output.
     */
    @Test
    public void pressing5OnMenuExits() {
        setupTest("5", new Session());
        startCapture();
        cli.start();
        String[] output = out.toString().split("\n");
        stopCapture();
        assertEquals("> Thank you for using Viitemanageri!", output[7]);
    }

    @Test
    public void pressing1AddsReference() {
        setupTest("1\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\n5", new Session());
        startCapture();
        cli.start();
        String[] output = out.toString().split("\n");
        System.out.println(output[0]);
        stopCapture();
        assertEquals("Reference added:", output[9]);
    }

    @Test
    public void identifierMustNotBeEmpty() {
        setupTest("1\n\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\n5", new Session());
        startCapture();
        cli.start();
        String[] output = out.toString().split("\n");
        System.out.println(output[0]);
        stopCapture();
        assertEquals("identifier*: Required field!", output[8]);
    }

    @Test
    public void invalidInputDoesNothing() {
        setupTest("u\n5", new Session());
        startCapture();
        cli.start();
        String[] output = out.toString().split("\n");
        System.out.println(output[0]);
        stopCapture();
        assertEquals("> Please input a number.", output[7]);
    }

    @Test
    public void pressing2ShowsPreview() {
        Session session = getSessionWithArticle();
        setupTest("2\n5", session);
        startCapture();
        cli.start();
        String[] output = out.toString().split("\n");
        System.out.println(output[0]);
        stopCapture();
        assertEquals(">     author: Esko", output[7]);
    }

    @Test
    public void pressing3ShowsBibtexPreview() {
        Session session = getSessionWithArticle();
        setupTest("3\n5", session);
        startCapture();
        cli.start();
        String[] output = out.toString().split("\n");
        System.out.println(output[0]);
        stopCapture();
        assertEquals("    author = {Esko},", output[8]);
    }

    @Test
    public void pressing0SaysUnknownCommand() {
        // Testaus kunniaan!
        Session session = new Session();
        setupTest("0\n5\n", session);
        startCapture();
        cli.start();
        String output = out.toString();
        System.out.println(output);
        stopCapture();
        assertTrue(output.contains("Unknown command."));
    }
}
