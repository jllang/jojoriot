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
        fields.put("identifier", "esimerkki");
        fields.put("author", "Esko");
        fields.put("title", "Juttu");
        fields.put("journal", "Lehti");
        fields.put("year", "1666");
        Article article = new Article(fields);
        return article;
    }

    private static Session getSessionWithArticle() {
        Session session = new Session();
        session.add(getTestArticle());
        return session;
    }

    private static String runTest() {
        startCapture();
        cli.start();
        String output = out.toString();
        //System.out.println(output[0]);
        stopCapture();
        return output;
    }

    /**
     * Tests that the exiting line is on the correct line of the output.
     */
    @Test
    public void pressing8OnMenuExits() {
        setupTest("8", new Session());
        String output = runTest();
        assertTrue(output.contains("> Thank you for using Viitemanageri!"));
    }

    @Test
    public void pressing1AddsArticle() {
        setupTest("1\n1\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\n8", new Session());
        String output = runTest();
        assertTrue(output.contains("Reference added:"));
    }
    
    @Test
    public void pressing1AddsBook() {
        setupTest("1\n2\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\n8", new Session());
        String output = runTest();
        assertTrue(output.contains("Reference added:"));
    }
    
    @Test
    public void pressing1AddsInproceedings() {
        setupTest("1\n3\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\n8", new Session());
        String output = runTest();
        assertTrue(output.contains("Reference added:"));
    }

    @Test
    public void identifierMustNotBeEmpty() {
        setupTest("1\n1\n\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\n8", new Session());
        String output = runTest();
        assertTrue(output.contains("identifier*: Required field!"));
    }

    @Test
    public void identifierMustBeUnique() {
        setupTest("1\n1\nesimerkki\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\n8", getSessionWithArticle());
        String output = runTest();
        assertTrue(output.contains("identifier*: Not a unique identifier!"));
    }

    @Test
    public void invalidInputDoesNothing() {
        setupTest("u\n8", new Session());
        String output = runTest();
        assertTrue(output.contains("> Please input a number."));
    }

    @Test
    public void pressing2ShowsPreview() {
        Session session = getSessionWithArticle();
        setupTest("2\n8", session);
        String output = runTest();
        assertTrue(output.contains("    author: Esko"));
    }

    @Test
    public void pressing3ShowsBibtexPreview() {
        Session session = getSessionWithArticle();
        setupTest("3\n8", session);
        String output = runTest();
        assertTrue(output.contains("    author = {Esko},"));
    }

    @Test
    public void pressing0SaysUnknownCommand() {
        // Testaus kunniaan!
        Session session = new Session();
        setupTest("0\n8\n", session);
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
        setupTest("4\ntesti.bibtex\n8", session);
        String output = runTest();
        assertTrue(output.contains("> File exported to: testi.bibtex"));
        File f = new File("testi.bibtex");
        f.delete();
    }

    @Test
    public void invalidFilePathDoesNotCreateFile() {
        Session session = getSessionWithArticle();
        setupTest("4\n\n8", session);
        String output = runTest();
        assertTrue(output.contains("> Exporting bibtex file failed!"));
    }

    @Test
    public void pressing5DeletesReference() {
        Session session = getSessionWithArticle();
        setupTest("5\nesimerkki\n8", session);
        String output = runTest();
        assertTrue(output.contains("> Reference \"esimerkki\" deleted."));
    }

    @Test
    public void wrongIdentifierDoesNotDeleteReference() {
        Session session = getSessionWithArticle();
        setupTest("5\nesimerki\n8", session);
        String output = runTest();
        assertTrue(output.contains("> Identifier \"esimerki\" does not match any reference."));
    }

    @Test
    public void emptyReferenceListing() {
        setupTest("2\n3\n8", new Session());
        String output = runTest();
        assertTrue(output.contains("> (No references.)"));
    }
    
    @Test
    public void missingReferenceEdit() {
        setupTest("7\nasd\n8", getSessionWithArticle());
        String output = runTest();
        assertTrue(output.contains("does not match any reference."));
    }
    
    @Test
    public void invalidReferenceEdit() {
        setupTest("7\nesimerkki\n \n8", getSessionWithArticle());
        String output = runTest();
        assertTrue(output.contains("Can't delete a required "));
    }
    
    @Test
    public void validReferenceEdit() {
        setupTest("7\nesimerkki\nasd\n\n\n\n\n8", getSessionWithArticle());
        String output = runTest();
        assertTrue(output.contains("Reference \"esimerkki\" edited."));
    }
    
    @Test
    public void kirjottakaaJokuMuu() {
        setupTest("6\ntest.bibtex\n8", new Session());
        String output = runTest();
    }
}
