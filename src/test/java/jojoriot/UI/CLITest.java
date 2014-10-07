package jojoriot.UI;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Scanner;
import jojoriot.references.Article;
import jojoriot.viitemanageri.Session;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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

    private static Article getTestArticle() {
        LinkedHashMap<String, String> fields = new LinkedHashMap<>();
        fields.put("author", "Esko");
        fields.put("title", "Juttu");
        fields.put("journal", "Lehti");
        fields.put("year", "1666");
        Article article = new Article("esimerkki", fields);
        return article;
    }

    private static Session getSessionWithArticle() {
        Session session = new Session();
        session.add(getTestArticle());
        return session;
    }

    private static String[] runTest() {
        startCapture();
        cli.start();
        String[] output = out.toString().split("\n");
        System.out.println(output[0]);
        stopCapture();
        return output;
    }

    /**
     * Tests that the exiting line is on the correct line of the output.
     */
    @Test
    public void pressing6OnMenuExits() {
        setupTest("7", new Session());
        startCapture();
        cli.start();
        String[] output = out.toString().split("\n");
        stopCapture();
        assertEquals("> Thank you for using Viitemanageri!", output[9]);
    }

    @Test
    public void pressing1AddsReference() {
        setupTest("1\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\n7", new Session());
        String[] output = runTest();
        assertEquals("Reference added:", output[11]);
    }

    @Test
    public void identifierMustNotBeEmpty() {
        setupTest("1\n\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\n7", new Session());
        String[] output = runTest();
        assertEquals("identifier*: Required field!", output[10]);
    }

    @Test
    public void identifierMustBeUnique() {
        setupTest("1\nesimerkki\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\n7", getSessionWithArticle());
        String[] output = runTest();
        assertEquals("identifier*: Not a unique identifier!", output[10]);
    }

    @Test
    public void invalidInputDoesNothing() {
        setupTest("u\n7", new Session());
        String[] output = runTest();
        assertEquals("> Please input a number.", output[9]);
    }

    @Test
    public void pressing2ShowsPreview() {
        Session session = getSessionWithArticle();
        setupTest("2\n7", session);
        String[] output = runTest();
        assertEquals("    author: Esko", output[11]);
    }

    @Test
    public void pressing3ShowsBibtexPreview() {
        Session session = getSessionWithArticle();
        setupTest("3\n7", session);
        String[] output = runTest();
        assertEquals("    author = {Esko},", output[10]);
    }

    @Test
    public void pressing0SaysUnknownCommand() {
        // Testaus kunniaan!
        Session session = new Session();
        setupTest("0\n7\n", session);
        startCapture();
        cli.start();
        String output = out.toString();
        System.out.println(output);
        stopCapture();
        assertTrue(output.contains("Unknown command."));
    }

    @Test
    public void pressing4ExportsFile() {
        Session session = getSessionWithArticle();
        setupTest("4\ntesti.bibtex\n7", session);
        String[] output = runTest();
        assertEquals("> File exported to: testi.bibtex", output[10]);
        File f = new File("testi.bibtex");
        f.delete();
    }

    @Test
    public void invalidFilePathDoesNotCreateFile() {
        Session session = getSessionWithArticle();
        setupTest("4\n\n7", session);
        String[] output = runTest();
        assertEquals("> Exporting bibtex file failed!", output[10]);
    }

    @Test
    public void pressing5DeletesReference() {
        Session session = getSessionWithArticle();
        setupTest("5\nesimerkki\n7", session);
        String[] output = runTest();
        assertEquals("> Reference \"esimerkki\" deleted.", output[10]);
    }

    @Test
    public void wrongIdentifierDoesNotDeleteReference() {
        Session session = getSessionWithArticle();
        setupTest("5\nesimerki\n7", session);
        String[] output = runTest();
        assertEquals("> Identifier \"esimerki\" does not match any reference.",
                output[10]);
    }

    @Test
    public void emptyReferenceListing() {
        setupTest("2\n3\n7", new Session());
        String[] output = runTest();
        assertEquals("> (No references.)",output[9]);
        assertEquals("> (No references.)",output[18]);
    }
}
