package jojoriot.viitemanageri;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
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

        final LinkedHashMap<String, String> fields = new LinkedHashMap<>();
        fields.put("identifier", "test");
        fields.put("author", "asd");
        fields.put("title", "asd");
        fields.put("journal", "title");
        fields.put("year", "asd");
        fields.put("volume", "asd");

        reference = new Article(fields);
        session.add(reference);
    }

    @Test
    public void addingReferencesWorks() {
        assertTrue(session.getReferences().contains(reference));
    }

    @Test
    public void removingReferencesWorks() {
        session.remove(reference);
        assertFalse(session.getReferences().contains(reference));
    }

    @Test
    public void deletingExistingReference() {
        session.delete("test");
        assertFalse(session.getReferences().contains(reference));
    }

    @Test(expected=NoSuchElementException.class)
    public void deletingNonExistingReference() {
        session.delete("testi");
    }

    @Test
    public void savingAndLoadingBibtexFiles() {
        final LinkedHashMap<String, String> fields = new LinkedHashMap<>();
        fields.put("identifier", "test2");
        fields.put("author", "Johnny");
        fields.put("title", "Second test article");
        fields.put("journal", "Jojoriot");
        fields.put("year", "2014");
        fields.put("volume", "I");

        session.add(new Article(fields));
        try {
            session.save("SessionTest.bib");
        } catch (final FileNotFoundException ex) {
            fail("Writing references to a bibtex file failed, so the test will "
                    + "end inconclusively.");
        }
        final File testFile = new File("SessionTest.bib");
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
        try {
            session.load("SessionTest.bib");
        } catch (FileNotFoundException ex) {
            fail("Reading references from a bibtex file failed, so the test "
                    + "will end inconclusively.");
        } finally {
            testFile.delete();
        }
    }

    @Test
    public void uniqueIdentifierWorksWhenIdentifierCrazyUnique() {
        assertTrue(session.isUniqueIdentifier("asdasd"));
    }

    @Test
    public void uniqueIdentifierFalseWhenIdentifierNotUnique() {
        assertFalse(session.isUniqueIdentifier("test"));
    }
}
