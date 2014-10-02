package jojoriot.viitemanageri;

import java.util.LinkedHashMap;
import jojoriot.references.Article;
import jojoriot.references.Reference;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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
}
