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
        
        reference = new Article("test",
                new LinkedHashMap<String, String>());
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
