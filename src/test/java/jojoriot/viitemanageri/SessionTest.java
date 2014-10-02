package jojoriot.viitemanageri;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Scanner;
import jojoriot.references.Article;
import jojoriot.references.Reference;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

public class SessionTest {
    Session session;
    Reference reference;

    @Before
    public void setUp() {
        session = new Session();

        LinkedHashMap<String, String> fields = new LinkedHashMap<>();
        fields.put("author", "asd");
        fields.put("title", "asd");
        fields.put("journal", "title");
        fields.put("year", "asd");
        fields.put("volume", "asd");

        reference = new Article("test", fields);
    }

    @Test
    public void addingReferencesWorks() {
        session.add(reference);
        assertTrue(session.getReferences().contains(reference));
    }

    @Test
    public void removingReferencesWorks() {
        session.add(reference);
        session.remove(reference);
        assertFalse(session.getReferences().contains(reference));
    }

    @Test
    public void exportingReferencesWorks() {
        final LinkedHashMap<String, String> fields = new LinkedHashMap<>();
        fields.put("author", "Johnny");
        fields.put("title", "Second test article");
        fields.put("journal", "Jojoriot");
        fields.put("year", "2014");
        fields.put("volume", "I");
        session.add(reference);
        session.add(new Article("test2", fields));
        try {
            session.export("test.bibtex");
        } catch (FileNotFoundException ex) {
            fail("Writing references to a bibtex file failed, so the test will "
                    + "end inconclusively.");
        }
        final File testFile = new File("test.bibtex");
        try {
            final Scanner s = new Scanner(testFile);
            int lines = 0;
            // Tiedoston sisällön voisi ehkä tarkistaa muutenkin kuin pelkän
            // rivimäärän perusteella...
            while (s.hasNextLine()) {
                s.nextLine();
                lines++;
            }
            assertEquals(14, lines);
        } catch (FileNotFoundException ex) {
            fail("The test file was not written into!");
        }
    }
}
