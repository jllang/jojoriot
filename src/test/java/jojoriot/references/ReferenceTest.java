package jojoriot.references;

import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

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
                + "author = {asd},\n"
                + "title = {asd},\n"
                + "journal = {asd},\n"
                + "year = {asd},\n}", article.toBibtexString());
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
            super("", null, null);
        }
    }
}
