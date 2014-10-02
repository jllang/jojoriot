package jojoriot.references;

import java.util.LinkedHashMap;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ArticleTest {
    private Article article;
    private LinkedHashMap<String, String> fields;
    
    @Before
    public void setUp() {
        fields = new LinkedHashMap<>();
        fields.put("author", "asd");
        fields.put("title", "asd");
        fields.put("journal", "title");
        fields.put("year", "asd");
        fields.put("volume", "asd");
        
        article = new Article("test", fields);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void constructorFailsOnEmptyRequiredField() {
        fields.put("author", "");
        article = new Article("test", fields);
    }

    @Test(expected=IllegalArgumentException.class)
    public void constructorFailsOnInvalidField() {
        fields.put("asd", "");
        article = new Article("test", fields);
    }
    
    @Test
    public void getGetsFields() {
        assertEquals(article.get("author"), "asd");
        assertEquals(article.get("volume"), "asd");
    }
    
    @Test
    public void deleteDeletesFields() {
        article.delete("volume");
        assertNull(article.get("volume"));
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void deletingFailsOnRequiredFields() {
        article.delete("author");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void constructorFailsOnMissingRequiredField() {
        fields.remove("author");
        article = new Article("test", fields);
    }
}
