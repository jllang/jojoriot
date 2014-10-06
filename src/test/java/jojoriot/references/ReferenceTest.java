package jojoriot.references;

import java.util.LinkedHashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for methods in abstract Reference class
 * excluding put and checkValue.
 */
public class ReferenceTest {
    Article article;
    LinkedHashMap<String, String> requiredFields;

    @Before
    public void setUp() {
        requiredFields = new LinkedHashMap<>();

        requiredFields.put("author", "asd");
        requiredFields.put("title", "asd");
        requiredFields.put("journal", "asd");
        requiredFields.put("year", "asd");

        article = new Article("asd", requiredFields);
    }

    @Test
    public void bibTeXinLuontiOnnistuu() {
        assertEquals("@Article{asd,\n"
                + "    author = {asd},\n"
                + "    title = {asd},\n"
                + "    journal = {asd},\n"
                + "    year = {asd},\n}", article.toBibtexString());
    }

    @Test
    public void kaikenDatanSaaTuotua() {
        final Map<String, String> data = article.getData();
        for (final Map.Entry<String, String> entry : data.entrySet()) {
            assertTrue(requiredFields.containsKey(entry.getKey()));
            assertEquals(requiredFields.get(entry.getKey()), entry.getValue());
        }
    }

    public class ReferenceImpl extends Reference {
        public ReferenceImpl() {
            super("", null, null, null);
        }
    }

    @Test
    public void inproceedingsFakeout() {
        requiredFields = new LinkedHashMap<>();
        
        requiredFields.put("author", "asd");
        requiredFields.put("title", "asd");
        requiredFields.put("booktitle", "asd");
        requiredFields.put("year", "asd");
        
        Inproceedings ref = new Inproceedings("test", requiredFields);
    }

    @Test
    public void bookFakeout() {
        requiredFields = new LinkedHashMap<>();

        requiredFields.put("author", "asd");
        requiredFields.put("title", "asd");
        requiredFields.put("publisher", "asd");
        requiredFields.put("year", "asd");
        
        Book ref = new Book("test", requiredFields);
    }
    
    @Test
    public void testaDiaeresis() {
        article.put("author", "äÄöÖåÅ");
        System.out.println(article.toBibtexString());
        assertTrue(article.toBibtexString().contains("\\\"{a}\\\"{A}\\\"{o}\\\"{O}\\aa\\AA"));
    }
}
