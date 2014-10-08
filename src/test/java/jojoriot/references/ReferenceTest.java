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
    LinkedHashMap<String, String> fields;

    @Before
    public void setUp() {
        fields = new LinkedHashMap<>();

        fields.put("identifier", "asd");
        fields.put("author", "asd");
        fields.put("title", "asd");
        fields.put("journal", "asd");
        fields.put("year", "asd");

        article = new Article(fields);
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
            assertTrue(fields.containsKey(entry.getKey()));
            assertEquals(fields.get(entry.getKey()), entry.getValue());
        }
    }

    public class ReferenceImpl extends Reference {
        public ReferenceImpl() {
            super(null, null, null);
        }
    }

    @Test
    public void inproceedingsFakeout() {
        fields = new LinkedHashMap<>();
        
        fields.put("identifier", "test");
        fields.put("author", "asd");
        fields.put("title", "asd");
        fields.put("booktitle", "asd");
        fields.put("year", "asd");
        
        Inproceedings ref = new Inproceedings(fields);
    }

    @Test
    public void bookFakeout() {
        fields = new LinkedHashMap<>();

        fields.put("identifier", "test");
        fields.put("author", "asd");
        fields.put("title", "asd");
        fields.put("publisher", "asd");
        fields.put("year", "asd");
        
        Book ref = new Book(fields);
    }
    
    @Test
    public void testaDiaeresis() {
        article.put("author", "äÄöÖåÅ");
        System.out.println(article.toBibtexString());
        assertTrue(article.toBibtexString().contains("\\\"{a}\\\"{A}\\\"{o}\\\"{O}\\aa\\AA"));
    }
}
