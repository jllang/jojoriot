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
    Article art;
    LinkedHashMap<String, String> requiredFields;
    
    @Before
    public void setUp() {
        requiredFields = new LinkedHashMap<String, String>();
        
        requiredFields.put("author", "asd");
        requiredFields.put("title", "asd");
        requiredFields.put("journal", "asd");
        requiredFields.put("year", "asd");
        
        art = new Article("asd", requiredFields);
    }
    
    @Test
    public void bibTextinLuontiOnnistuu() {
        assertEquals("@Article{asd,\nauthor = {asd},\ntitle = {asd},\njournal = {asd},\nyear = {asd},\n}", art.toBibtexString());
    }
    
    @Test
    public void kaikenDatanSaaTuotua() {
        Map<String, String> data = art.getData();
        for (final Map.Entry<String, String> entry : data.entrySet()) {
            assertTrue(requiredFields.containsKey(entry.getKey()));
            assertEquals(requiredFields.get(entry.getKey()), entry.getValue());
        }
    }
    
    public class ReferenceImpl extends Reference {
        public ReferenceImpl() {
            super("", null, null);
        }

        public void checkvalue(String key, String value) throws IllegalArgumentException {
        }
    }
}
